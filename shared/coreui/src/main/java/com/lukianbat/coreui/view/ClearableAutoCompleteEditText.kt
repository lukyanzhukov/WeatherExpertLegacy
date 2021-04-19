package com.lukianbat.coreui.view

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.res.ResourcesCompat
import com.lukianbat.coreui.R

class ClearableAutoCompleteEditText : AppCompatAutoCompleteTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    init {
        initClearOnTouchListener()
    }

    private var clearButtonDrawable: Drawable? = null

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)

        clearButtonDrawable = if (focused) {
            ResourcesCompat.getDrawable(resources, R.drawable.ic_clear, null)!!
        } else {
            null
        }

        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearButtonDrawable, null)
    }

    private fun initClearOnTouchListener() {
        setOnTouchListener(OnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.x >= (this.right - this.left - this.compoundPaddingRight)) {
                    this.setText("")
                }
            }
            return@OnTouchListener false
        })
    }
}