package com.lukianbat.architecture.data

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class MemoryCache<T>(private val expiration: Expiration) {

    @Volatile
    private var value: T? = null

    @Volatile
    private var loadTime: Long = 0

    private val values: Subject<T> = PublishSubject.create()
    private val errors: Subject<Throwable> = PublishSubject.create()
    
    fun set(value: T) {
        loadTime = System.currentTimeMillis()
        this.value = value
        values.onNext(value)
    }

    fun error(error: Throwable) {
        errors.onNext(error)
    }

    fun invalidate() {
        value = null
        loadTime = 0
    }

    fun observe(empty: () -> Unit): Observable<T> {
        return Observable.create<T> { emitter ->
            val dataDisposable = values
                    .subscribe {
                        if (!emitter.isDisposed) {
                            emitter.onNext(it)
                        }
                    }

            val errorDisposable = errors
                    .subscribe {
                        if (!emitter.isDisposed) {
                            emitter.onError(it)
                        }
                    }

            val memory = value
            if (memory == null || expired()) {
                empty()
            } else {
                emitter.onNext(memory)
            }

            emitter.setDisposable(CompositeDisposable(dataDisposable, errorDisposable))
        }
    }

    private fun expired(): Boolean = when(expiration) {
        Expiration.Never -> false
        is Expiration.Timed -> loadTime + expiration.unit.toMillis(expiration.duration) < System.currentTimeMillis()
    }

}