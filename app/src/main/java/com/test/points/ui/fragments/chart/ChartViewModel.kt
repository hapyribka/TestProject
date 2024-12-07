package com.test.points.ui.fragments.chart

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.points.R
import com.test.points.business.models.Point
import com.test.points.ui.fragments.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ChartViewModel() : BaseViewModel() {
    private val dispatcherIO = Dispatchers.IO

    private var _pointsListLiveData = MutableLiveData(ArrayList<Point>())
    var pointsListLiveData: LiveData<ArrayList<Point>> = _pointsListLiveData

    fun setPoints(array: Array<Point>) {
        val points = ArrayList<Point>()
        points.addAll(array)
        _pointsListLiveData.postValue(points)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun saveViewAsBitmap(view: View) {
        showLoaderLiveDataBase.postValue(true)
        viewModelScope.launch {
            runCatching {
                withContext(dispatcherIO) {
                    val fileName = "${System.currentTimeMillis()}.png"
                    if (isActive) {
                        val bitmapDef =
                            async {
                                getBitmapFromView(view)
                            }
                        bitmapDef.await()
                        if (isActive) {
                            saveBitmapToFile(bitmapDef.getCompleted(), fileName)
                        }
                    }
                }
            }.onSuccess {
                showLoaderLiveDataBase.postValue(false)
            }.onFailure {
                showLoaderLiveDataBase.postValue(false)
                errorLiveDataBase.postValue(Pair(R.string.error_save_to_file, " ${it.message}"))
            }
        }
    }

    private fun saveBitmapToFile(
        bitmap: Bitmap,
        fileName: String
    ) {
        val outputStream: FileOutputStream? = null
        try {
            val file =
                File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    fileName
                )
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            outputStream?.close()
            e.printStackTrace()
        }
        bitmap.recycle()
    }

    internal fun getBitmapFromView(view: View): Bitmap {
        val bitmap =
            Bitmap.createBitmap(
                view.width,
                view.height,
                Bitmap.Config.ARGB_8888
            )
        view.draw(Canvas(bitmap))
        return bitmap
    }
}
