package com.test.points.ui.fragments.points

import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.test.points.R
import com.test.points.business.models.Point
import com.test.points.repositories.network.NetworkApi
import com.test.points.repositories.preferences.Preferences
import com.test.points.ui.fragments.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PointsViewModel(
    val preferences: Preferences,
    val networkApi: NetworkApi
) : BaseViewModel() {
    private val dispatcherIO = Dispatchers.IO

    private var _pointsCountLiveData = MutableLiveData(preferences.getSavedPointsCount())
    var pointsCountLiveData: LiveData<String> = _pointsCountLiveData

    private var _enableButtonLiveData = MutableLiveData(false)
    var enableButtonLiveData: LiveData<Boolean> = _enableButtonLiveData

    fun onResume() {
        _pointsCountLiveData.postValue(preferences.getSavedPointsCount())
    }

    fun checkEnableButton(editable: Editable?) {
        preferences.savePointsCount(editable.toString())
        _enableButtonLiveData.postValue(!editable.isNullOrEmpty())
    }

    fun isValidCountText(editable: Editable?): Boolean {
        val valid = checkValidCountText(editable.toString())
        if (!valid) {
            errorLiveDataBase.postValue(Pair(R.string.error_wrong_format, ""))
            _enableButtonLiveData.postValue(false)
        }
        return valid
    }

    fun getPoints(
        navController: NavController,
        count: Int
    ) {
        viewModelScope.launch {
            showLoaderLiveDataBase.postValue(true)
            runCatching {
                withContext(dispatcherIO) {
                    networkApi.getPoints(count)
                }
            }.onSuccess {
                showLoaderLiveDataBase.postValue(false)
                if (it?.points.isNullOrEmpty()) {
                    errorLiveDataBase.postValue(Pair(R.string.error_empty_point_list, ""))
                } else {
                    if (isActive) {
                        launch(dispatcherIO) {
                            val sortedPoints = sortPointsByX(it.points)
                            withContext(Dispatchers.Main) {
                                val bundle = Bundle()
                                bundle.putParcelableArray("points", sortedPoints.toTypedArray())
                                navController.navigate(
                                    R.id.action_PointsFragment_to_ChartFragment,
                                    bundle
                                )
                            }
                        }
                    }
                }
            }.onFailure {
                showLoaderLiveDataBase.postValue(false)
                errorLiveDataBase.postValue(Pair(R.string.error_network, " ${it.message}"))
            }
        }
    }

    companion object {
        fun checkValidCountText(input: String?): Boolean {
            input?.let {
                try {
                    val count = it.trim().toInt()
                    if (count > 0) {
                        return true
                    }
                } catch (_: NumberFormatException) {
                }
            }
            return false
        }

        fun sortPointsByX(list: List<Point>): ArrayList<Point> {
            val sortedPoints: ArrayList<Point> = ArrayList()
            var sortedList = list.sortedWith(compareBy { it.x })
            sortedPoints.addAll(sortedList)
            return sortedPoints
        }
    }
}
