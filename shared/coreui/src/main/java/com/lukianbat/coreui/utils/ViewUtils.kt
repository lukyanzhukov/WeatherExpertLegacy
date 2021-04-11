@file:Suppress("NOTHING_TO_INLINE")

package com.lukianbat.coreui.utils

import android.app.Activity
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Observable

fun <T : View> Activity.bindView(@IdRes res: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
}

fun <T : View> RecyclerView.ViewHolder.bindView(@IdRes res: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { itemView.findViewById<T>(res) }
}

fun <T : View> View.bindView(@IdRes res: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
}

fun RecyclerView.ViewHolder.getString(@StringRes res: Int): String {
    return itemView.context.getString(res)
}

inline fun ViewGroup.inflate(@LayoutRes resId: Int): View = LayoutInflater.from(context).inflate(resId, this, false)

inline fun ViewGroup.inflate(@LayoutRes resId: Int, attach: Boolean = false, setup: View.() -> Unit): View {
    val view = LayoutInflater.from(context).inflate(resId, this, false)
    view.apply(setup)
    if (attach) {
        addView(view)
    }
    return view
}

fun FloatingActionButton.bindToRecyclerView(recyclerView: RecyclerView) {
    val fab = this
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0 && fab.isVisible) {
                fab.hide()
            } else if (dy < 0 && !fab.isVisible) {
                fab.show()
            }
        }
    })
}

fun Lifecycle.asObservable(): Observable<Lifecycle.Event> {
    return Observable.create { emitter ->
        val observer = object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() = emitter.onNext(Lifecycle.Event.ON_CREATE)

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() = emitter.onNext(Lifecycle.Event.ON_START)

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() = emitter.onNext(Lifecycle.Event.ON_RESUME)

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() = emitter.onNext(Lifecycle.Event.ON_PAUSE)

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() = emitter.onNext(Lifecycle.Event.ON_STOP)

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() = emitter.onNext(Lifecycle.Event.ON_DESTROY)
        }
        addObserver(observer)
        emitter.setCancellable { removeObserver(observer) }
    }
}

fun Lifecycle.asObservable(vararg lifeCycleEvents: Lifecycle.Event): Observable<Lifecycle.Event> {
    return asObservable().filter { it in lifeCycleEvents }
}

fun View.entryName() = resources.getResourceEntryName(id)

fun convertIntToDp(resources: Resources, px: Int): Int {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            px.toFloat(),
            resources.displayMetrics
    ).toInt()
}

var TextView.compoundDrawableRight: Drawable?
    get() = compoundDrawables[2]
    set(value) {
        setCompoundDrawables(
                compoundDrawables[0],
                compoundDrawables[1],
                value,
                compoundDrawables[3]
        )
    }

fun Boolean.toVisibility() = if (this) {
    View.VISIBLE
} else {
    View.INVISIBLE
}