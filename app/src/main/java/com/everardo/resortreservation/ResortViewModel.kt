package com.everardo.resortreservation

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author everardo.salazar on 1/30/19
 */
class ResortViewModel(private val myApp: Application) : AndroidViewModel(myApp) {
    val data: LiveData<String>
    private val resortCache = ResortCache(myApp.applicationContext)

    init {
        //TODO set up content resolver and use it with an Observable with RxJava
        data = LiveDataReactiveStreams.fromPublisher(resortCache.getResortReservations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable(BackpressureStrategy.LATEST))
    }

    fun saveText(text: String) {
        //TODO save text to database using RxJava
        resortCache.saveResortReservation(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}