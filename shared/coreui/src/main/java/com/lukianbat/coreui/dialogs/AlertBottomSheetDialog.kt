package com.lukianbat.coreui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lukianbat.coreui.R
import kotlinx.android.synthetic.main.alert_bottom_sheet.view.*
import kotlinx.android.synthetic.main.base_bottom_sheet.view.*

open class AlertBottomSheetDialog private constructor(
    context: Context,
    private val title: String,
    private val message: String,
    private val positiveText: String?,
    private val negativeText: String?,
    private val negativeChoiceClickListener: ((AlertBottomSheetDialog) -> Unit)?,
    private val positiveChoiceClickListener: ((AlertBottomSheetDialog) -> Unit)?,
    private val onDismissListener: ((AlertBottomSheetDialog) -> Unit)?,
    private val cancelable: Boolean?
) : BottomSheetDialog(context) {

    init {
        setOnDismissListener { onDismissListener?.invoke(this) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = layoutInflater.inflate(R.layout.base_bottom_sheet, null, false)

        setContentView(rootView)
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun setContentView(view: View) {
        super.setContentView(view)

        val titleView = view.findViewById(R.id.textView_titleBaseBottomSheet) as TextView
        titleView.text = title

        val childView = layoutInflater.inflate(R.layout.alert_bottom_sheet, view.baseBottomSheet_root, false)

        childView.textView_messageAlertBottomSheet.text = message
        setCancelable(cancelable ?: false)

        childView.textView_negativeAlertBottomSheet.isVisible = negativeChoiceClickListener != null
        childView.textView_negativeAlertBottomSheet.setOnClickListener {
            dismiss()
            negativeChoiceClickListener?.invoke(this)
        }

        positiveText?.let { childView.textView_positiveAlertBottomSheet.text = it }
        negativeText?.let { childView.textView_negativeAlertBottomSheet.text = it }
        childView.textView_positiveAlertBottomSheet.setOnClickListener {
            dismiss()
            positiveChoiceClickListener?.invoke(this)
        }

        view.baseBottomSheet_root.addView(childView)
    }

    class Builder(private val context: Context) {
        private var title: String? = null
        private var message: String? = null
        private var negativeClickListener: ((dialog: AlertBottomSheetDialog) -> Unit)? = null
        private var positiveClickListener: ((dialog: AlertBottomSheetDialog) -> Unit)? = null
        private var onDismissListener: ((dialog: AlertBottomSheetDialog) -> Unit)? = null
        private var positiveText: String? = null
        private var cancelable: Boolean? = null
        private var negativeText: String? = null

        fun setTitle(title: String) = apply { this.title = title }
        fun setTitle(@StringRes res: Int) = apply { this.title = context.getString(res) }
        fun setMessage(message: String) = apply { this.message = message }
        fun setMessage(@StringRes res: Int) = apply { this.message = context.getString(res) }
        fun setPositiveButton(@StringRes res: Int, func: (AlertBottomSheetDialog) -> Unit = {}) = apply {
            this.positiveText = context.getString(res); this.positiveClickListener = func
        }

        fun setNegativeButton(@StringRes res: Int, func: (AlertBottomSheetDialog) -> Unit = {}) = apply {
            this.negativeText = context.getString(res); this.negativeClickListener = func
        }

        fun setNegativeClickListener(func: (AlertBottomSheetDialog) -> Unit) = apply {
            this.negativeClickListener = func
        }

        fun setPositiveClickListener(func: (AlertBottomSheetDialog) -> Unit) = apply {
            this.positiveClickListener = func
        }

        fun setOnDismissListener(listener: (AlertBottomSheetDialog) -> Unit) = apply {
            this.onDismissListener = listener
        }

        fun setCancelable(cancelable: Boolean) = apply { this.cancelable = cancelable }

        fun build() = AlertBottomSheetDialog(
            context,
            title.orEmpty(),
            message.orEmpty(),
            positiveText,
            negativeText,
            negativeClickListener,
            positiveClickListener,
            onDismissListener,
            cancelable
        )

        fun show() = build().apply { show() }
    }
}