package com.starcodex.gorillatest.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.starcodex.gorillatest.data.remote.ConfigItem
import com.starcodex.gorillatest.data.IceRepository
import com.starcodex.gorillatest.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainViewModel @Inject constructor(
    var iceRepository: IceRepository
) : ViewModel() {

    fun resetCart() {
        iceRepository.deleteAllFromTable()
    }

}