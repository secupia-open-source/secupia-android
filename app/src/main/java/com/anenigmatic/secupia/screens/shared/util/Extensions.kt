package com.anenigmatic.secupia.screens.shared.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Reduces the code we need to type for casting LiveData to MutableLiveData.
 * */
fun <T> LiveData<T>.asMut(): MutableLiveData<T> {
    return (this as? MutableLiveData<T>) ?: throw IllegalArgumentException("Not a MutableLiveData")
}

/**
 * Makes CompositeDisposable hold only one disposable at a time. It is here
 * just  because I prefer  CompositeDisposable.set(disposable)  syntax over
 * Disposable = disposable syntax.
 * */
fun CompositeDisposable.set(disposable: Disposable) {
    clear()
    add(disposable)
}