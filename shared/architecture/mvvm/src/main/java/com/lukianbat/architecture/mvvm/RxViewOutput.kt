package com.lukianbat.architecture.mvvm

import android.content.res.Resources
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException


class RxViewOutput<T>(disposeBy: DisposableContainer, strategy: Strategy = Strategy.LAST): Disposable, ViewOutput<T> {

    private var disposable: Disposable? = null

    private val retry = PublishSubject.create<Any>()
    private val starter = BehaviorSubject.createDefault(Any())
    private val valueHolder = when(strategy) {
        Strategy.ONCE -> SingleLiveData<State<T>>()
        Strategy.LAST -> MutableLiveData<State<T>>()
    }

    init {
        disposeBy.add(this)
    }

    override fun isDisposed(): Boolean {
        val current = disposable
        current?: return true

        return current.isDisposed
    }

    override fun dispose() {
        val current = disposable
        current ?: return

        current.dispose()
    }

    /**
     * Use only for initial value
     */
    fun valueSource(data: T) {
        source(Observable.just(data), ErrorAdapter.DEFAULT)
    }

    fun source(source: Observable<T>, errorAdapter: ErrorAdapter) {
        val previous = disposable
        if (previous != null && !previous.isDisposed) {
            previous.dispose()
        }

        disposable = starter
                .switchMap {
                    source.compose(resultTransformer(errorAdapter))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    valueHolder.value = it
                }
    }

    fun retry() {
        if (valueHolder.value is State.Error) {
            retry.onNext(Any())
        }
    }

    override fun observe(owner: LifecycleOwner, observer: (State<T>) -> Unit) {
        valueHolder.observe(owner, Observer {
            if (it == null)
                return@Observer

            observer(it)
        })
    }

    override fun observeData(owner: LifecycleOwner, observer: (T) -> Unit) {
        observe(owner) {
            when (it) {
                is State.Completed -> observer(it.data)
            }
        }
    }

    override fun restart() {
        starter.onNext(Any())
    }

    fun asOutput(): ViewOutput<T> = this

    private fun resultTransformer(errorAdapter: ErrorAdapter): ObservableTransformer<T, State<T>> {
        return ObservableTransformer { upstream ->
            upstream
                    .map {
                        State.Completed(it) as State<T>
                    }
                    .doOnSubscribe {
                        valueHolder.postValue(State.Loading)
                    }
                    .doOnError {
                        val message = errorAdapter.adapt(it)
                        valueHolder.postValue(State.Error(message, this::retry))
                    }
                    .retryWhen {
                        return@retryWhen retry
                    }
        }
    }

    interface ErrorAdapter {

        companion object {
            val DEFAULT = object: ErrorAdapter {
                override fun adapt(error: Throwable): String? {
                    return error.message
                }
            }

            fun connection(resources: Resources) = object : ErrorAdapter {
                override fun adapt(error: Throwable): String? = when(error) {
                    is SocketException,
                    is SocketTimeoutException,
                    is UnknownHostException,
                    is SSLException -> resources.getString(R.string.rxview_errors_network)
                    else -> null
                }
            }

            fun composite(vararg adapters: ErrorAdapter) = object : ErrorAdapter {
                override fun adapt(error: Throwable): String? {
                    return adapters
                        .asSequence()
                        .mapNotNull { it.adapt(error) }
                        .firstOrNull()
                }
            }
        }

        fun adapt(error: Throwable): String?
    }

    /**
     * ONCE — delivered only once, can be used for dialogs or pre-filled data
     * LAST — delivered last value available
     */
    enum class Strategy {
        ONCE,
        LAST
    }
}