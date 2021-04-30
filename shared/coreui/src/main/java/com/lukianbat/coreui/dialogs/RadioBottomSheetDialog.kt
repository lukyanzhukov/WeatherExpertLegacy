package com.lukianbat.coreui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lukianbat.coreui.R
import kotlinx.android.synthetic.main.base_bottom_sheet.view.*
import kotlinx.android.synthetic.main.radio_item_bottom_sheet.view.*

open class RadioBottomSheetDialog<T> constructor(
    context: Context,
    val items: List<T>,
    val title: String? = null,
    private val selectedIndex: Int = UNDEFINED_INDEX,
    private val onItemClickListener: (Int) -> Unit,
    private val convertItemFunc: (item: T) -> String = { it.toString() }
) : BottomSheetDialog(context) {

    private var itemsList = mutableListOf<T>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = layoutInflater.inflate(R.layout.base_bottom_sheet, null, false)

        setContentView(rootView)
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun setContentView(view: View) {
        super.setContentView(view)

        val titleView = view.findViewById(R.id.textView_titleBaseBottomSheet) as TextView
        title?.let { titleView.text = it }

        items.forEachIndexed { itemIndex, item ->
            val itemChildView =
                layoutInflater.inflate(
                    R.layout.radio_item_bottom_sheet,
                    view.baseBottomSheet_root,
                    false
                )

            itemChildView.radioButton_radioBottomSheet
            itemChildView.radioButton_radioBottomSheet.text = convertItemFunc(item)
            itemChildView.radioButton_radioBottomSheet.setOnClickListener {
                dismiss()
                onItemClickListener(itemIndex)
            }

            if (selectedIndex != UNDEFINED_INDEX && selectedIndex == itemIndex) {
                itemChildView.radioButton_radioBottomSheet.isChecked = true
            }

            view.baseBottomSheet_root.addView(itemChildView)
            itemsList.add(item)
        }
    }

    companion object {

        private const val UNDEFINED_INDEX = -1
    }
}