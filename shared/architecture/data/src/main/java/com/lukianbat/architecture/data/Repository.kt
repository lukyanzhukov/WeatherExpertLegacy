package com.lukianbat.architecture.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

abstract class Repository<T : Any>(expiration: Expiration = Expiration.Never) {

    private val memory: MemoryCache<T> = MemoryCache(expiration)

    @Volatile
    private var loading: Boolean = false

    @Volatile
    private var loadingDisposable: Disposable? = null
    private val loadIn: PublishSubject<Any> = PublishSubject.create()

    init {
        loadIn
                .debounce(100, TimeUnit.MILLISECONDS)
                .subscribe {
                    loadData()
                }
    }

    fun data(): Observable<T> {
        return memory.observe(::loadData)
    }

    fun reload() {
        invalidate()
        update()
    }

    fun update() {
        loadIn.onNext(Any())
    }

    fun invalidate() {
        val disposable = loadingDisposable
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
            loadingDisposable = null
        }

        memory.invalidate()
        loading = false
        onInvalidate()
    }

    protected abstract fun load(): Single<T>

    protected open fun onInvalidate() {}

    @Synchronized
    private fun loadData() {
        if (loading)
            return

        val disposable = loadingDisposable
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
            loadingDisposable = null
        }

        loadingDisposable = network()
                .subscribe({
                    memory.set(it)
                }, {
                    memory.error(it)
                })
    }

    private fun network(): Observable<T> {
        return load()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    loading = true
                }
                .doFinally {
                    loading = false
                }
                .toObservable()
    }
}