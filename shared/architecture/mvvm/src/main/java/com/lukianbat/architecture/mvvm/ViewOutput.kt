package com.lukianbat.architecture.mvvm

import androidx.lifecycle.LifecycleOwner

interface ViewOutput<T> {

    fun observe(owner: LifecycleOwner, observer: (State<T>) -> Unit)

    fun observeData(owner: LifecycleOwner, observer: (T) -> Unit)

    fun restart()
}