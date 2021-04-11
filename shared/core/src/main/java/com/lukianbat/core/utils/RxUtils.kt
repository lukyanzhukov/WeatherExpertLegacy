@file:Suppress("NOTHING_TO_INLINE")

package com.lukianbat.core.utils

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import java.util.concurrent.TimeUnit

/**
 * Returns value from lambda if [condition] is true, Completable.complete() otherwise
 */
object Completables {
    inline fun conditional(
        condition: Boolean,
        success: () -> Completable
    ): Completable {
        return if (condition) success() else Completable.complete()
    }
}

inline operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    this.add(disposable)
}

inline fun <T, R> Observable<T>.flatMapSingle(
    delayErrors: Boolean,
    noinline mapper: (T) -> SingleSource<out R>
): Observable<R> {
    return this.flatMapSingle(mapper, delayErrors)
}

inline fun <T, R> Observable<T>.flatMapSingle(
    delayErrors: Boolean,
    maxConcurrency: Int,
    noinline mapper: (T) -> Single<out R>
): Observable<R> {
    return this.flatMap({ mapper(it).toObservable() }, delayErrors, maxConcurrency)
}

fun createReverseTimerObservable(seconds: Long): Observable<Long> =
    Observable.interval(0, 1, TimeUnit.SECONDS)
        .take(seconds)
        .map { tick -> seconds - tick - 1 }

inline fun Completable.toSingle(): Single<Unit> = toSingleDefault(Unit)

inline fun <T> Completable.andThenJust(item: T): Single<T> = andThen(Single.just(item))

inline fun <T> Completable.flatMapSingle(noinline singleSupplier: () -> Single<T>): Single<T> {
    return andThen(Single.defer(singleSupplier))
}

inline fun Completable.mapError(crossinline mapper: (Throwable) -> Throwable): Completable {
    return onErrorResumeNext { Completable.error(mapper(it)) }
}

inline fun Completable.flatMap(noinline completableSupplier: () -> Completable): Completable {
    return andThen(Completable.defer(completableSupplier))
}

object Observables {
    inline fun <T1, T2, R> combineLatest(
        source1: Observable<T1>,
        source2: Observable<T2>,
        crossinline combineFunction: (T1, T2) -> R
    ) = Observable.combineLatest(
        source1,
        source2,
        BiFunction<T1, T2, R> { t1, t2 -> combineFunction(t1, t2) })

    inline fun <T1, T2, T3, R> combineLatest(
        source1: Observable<T1>,
        source2: Observable<T2>,
        source3: Observable<T3>,
        crossinline combineFunction: (T1, T2, T3) -> R
    ) = Observable.combineLatest(
        source1,
        source2,
        source3,
        Function3<T1, T2, T3, R> { t1, t2, t3 -> combineFunction(t1, t2, t3) })
}
