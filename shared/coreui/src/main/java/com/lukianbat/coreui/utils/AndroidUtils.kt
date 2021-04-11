@file:Suppress("NOTHING_TO_INLINE")

package com.lukianbat.coreui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.textfield.TextInputLayout
import java.lang.ref.SoftReference
import java.lang.ref.WeakReference

private var singleToast: SoftReference<Toast?> = SoftReference(null)

fun Context.toast(message: String, short: Boolean = false, single: Boolean = false) {
    val toast = Toast.makeText(
        applicationContext,
        message,
        if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    )
    if (single) {
        singleToast.get()?.cancel()
        singleToast = SoftReference(toast)
    }
    toast.show()
}

inline fun Fragment.toast(message: String, short: Boolean = false, single: Boolean = false) =
    this.requireContext().toast(message, short, single)

fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = currentFocus ?: View(this)
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    val inputMethodManager =
        context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = view!!.rootView
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

var TextInputLayout.text: String
    get() = this.editText?.text?.toString() ?: ""
    set(value) {
        this.editText?.setText(value) ?: return
    }

fun EditText.addAfterTextChangedListener(listener: (String) -> Unit) =
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable) = listener(s.toString())
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    })

inline val TextInputLayout.theEditText get() = editText!!
inline fun TextInputLayout.editText(withEditText: EditText.() -> Unit) =
    this.editText?.apply(withEditText)

inline fun <reified T : Activity> Context.startActivity() =
    startActivity(Intent(this, T::class.java))

fun View.dpToPx(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Context.dpToPx(value: Int): Int = (value * resources.displayMetrics.density).toInt()
fun Fragment.dpToPx(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.color(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)
fun View.color(@ColorRes color: Int): Int = ContextCompat.getColor(this.context, color)
fun Fragment.color(@ColorRes color: Int): Int = ContextCompat.getColor(context!!, color)
fun Context.dimen(@DimenRes dimen: Int): Int = this.resources.getDimension(dimen).toInt()
fun View.dimen(@DimenRes dimen: Int): Int = this.resources.getDimension(dimen).toInt()

inline fun Resources.Theme.getColorAttribute(@AttrRes name: Int): Int {
    val typedValue = TypedValue()
    resolveAttribute(name, typedValue, true)
    val arr = this.obtainStyledAttributes(typedValue.data, intArrayOf(name))
    val color = arr.getColor(0, -1)
    arr.recycle()
    return color
}

inline fun Fragment.setKeepScreenOn(keepScreenOn: Boolean) =
    activity!!.setKeepScreenOn(keepScreenOn)

inline fun Activity.setKeepScreenOn(keepScreenOn: Boolean) {
    if (keepScreenOn) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}

inline fun <T> Fragment.argument(crossinline getArgument: Bundle.() -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        val arguments = arguments ?: throw IllegalArgumentException("Fragment has no arguments")
        arguments.getArgument()
    }
}

inline fun <T> Activity.argument(crossinline getArgument: Intent.() -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        intent.getArgument()
    }
}

/**
 * Dismisses this dialog when onDestroy occurs
 */
inline fun <T : DialogInterface> T.bindToLifecycle(lifecycle: Lifecycle): T = apply {
    lifecycle.addObserver(object : LifecycleObserver {
        private val dialog = WeakReference(this@bindToLifecycle)

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroyView() {
            dialog.get()?.dismiss()
        }
    })
}

inline fun Context.checkSelfPermission2(permission: String) =
    ContextCompat.checkSelfPermission(this, permission)

val View.activity: Activity?
    get() {
        var context: Context? = context
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        return null
    }