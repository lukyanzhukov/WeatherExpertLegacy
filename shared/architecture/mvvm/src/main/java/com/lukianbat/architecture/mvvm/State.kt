package com.lukianbat.architecture.mvvm

sealed class State<out T> {
    /**
     * Emits on first subscribe or in case on restart
     */
    object Loading: State<Nothing>()

    /**
     * Emits on error, provide retry-block which allows to restart original rx-stream
     */
    class Error(val error: String?, private val retry: (() -> Unit) = {}): State<Nothing>() {
        fun retry() {
            retry.invoke()
        }
    }

    /**
     * Emits typed result of subscription, can emit multiple times
     */
    data class Completed<T>(val data: T): State<T>()
}