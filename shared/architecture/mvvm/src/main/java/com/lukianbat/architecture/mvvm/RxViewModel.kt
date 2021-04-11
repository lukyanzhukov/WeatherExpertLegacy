package com.lukianbat.architecture.mvvm

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

open class RxViewModel: ViewModel(), DisposableContainer {

    private val disposables = CompositeDisposable()

    override fun add(disposable: Disposable): Boolean = disposables.add(disposable)

    override fun remove(disposable: Disposable): Boolean = disposables.remove(disposable)

    override fun delete(disposable: Disposable): Boolean = disposables.delete(disposable)

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

fun Disposable.disposeBy(container: DisposableContainer)  {
    container.add(this)
}
