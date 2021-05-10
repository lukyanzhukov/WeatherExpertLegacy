package com.lukianbat.coreui.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class ViewBindingDelegate<T>(
    private val fragment: Fragment,
    private val bindingProvider: (View) -> T
) : ReadOnlyProperty<Any, T> {

    var binding: T? = null

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment, Observer { viewLifecycleOwner ->
            viewLifecycleOwner ?: return@Observer
            viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    binding = null
                }
            })
        })
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val aBinding = binding
        if (aBinding != null) return aBinding
        val view = fragment.view ?: throw IllegalStateException("Attempt to use binding before onViewCreated()")
        return bindingProvider(view).also { binding = it }
    }
}

/**
 * Property delegate to ease the usage of view binding. Sample:
 *
 * `private val binding by viewBinding { MyLayoutBinding.bind(requireView()) }`
 */
fun <T> Fragment.viewBinding(bindingProvider: (View) -> T): ReadOnlyProperty<Any, T> {
    return ViewBindingDelegate(this, bindingProvider)
}