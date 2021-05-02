package com.lukianbat.coreui.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.lukianbat.coreui.R
import com.lukianbat.coreui.utils.addAfterTextChangedListener
import kotlinx.android.synthetic.main.view_weather_expert_field.view.*

class WeatherExpertFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val attrsTypedArray: TypedArray

    init {
        val layout = R.layout.view_weather_expert_field
        val styleable = R.styleable.WeatherExpertFieldView
        LayoutInflater.from(context).inflate(layout, this, true)
        attrsTypedArray = context.theme.obtainStyledAttributes(attrs, styleable, 0, 0)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initView()
    }

    fun setText(text: String) {
        descriptionEditText.setText(text)
    }

    fun getText(): String = descriptionEditText.text.toString()

    fun setLabel(label: String) {
        labelTextView.text = label
    }

    fun setLocked(enabled: Boolean) {
        descriptionTextInputLayout.isEnabled = !enabled
    }

    fun setLines(count: Int) {
        descriptionEditText.minLines = count
    }

    fun addAfterTextChangedListener(listener: (String) -> Unit) {
        descriptionEditText.addAfterTextChangedListener { listener(it) }
    }

    private fun initView() {
        descriptionTextInputLayout.isEnabled =
            !(attrsTypedArray.getBoolean(R.styleable.WeatherExpertFieldView_locked, false))
        labelTextView.text = attrsTypedArray.getText(R.styleable.WeatherExpertFieldView_label) ?: ""
        descriptionEditText.minLines =
            attrsTypedArray.getInteger(R.styleable.WeatherExpertFieldView_defaultLines, 1)
        descriptionEditText.setText(
            attrsTypedArray.getString(R.styleable.WeatherExpertFieldView_text) ?: ""
        )
    }
}
