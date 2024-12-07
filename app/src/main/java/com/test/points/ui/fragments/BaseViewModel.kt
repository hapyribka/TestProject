package com.test.points.ui.fragments

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel() : ViewModel(), DefaultLifecycleObserver {
    protected var showLoaderLiveDataBase = MutableLiveData(false)
    var showLoaderLiveData: LiveData<Boolean> = showLoaderLiveDataBase

    protected var errorLiveDataBase = MutableLiveData(Pair(-1, ""))
    var errorLiveData: LiveData<Pair<Int, String>> = errorLiveDataBase

    fun cleanError() {
        errorLiveDataBase.postValue(Pair(-1, ""))
    }
}
