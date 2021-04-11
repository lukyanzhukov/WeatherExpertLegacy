package com.lukianbat.coreui.utils

import android.graphics.Canvas
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Attaches swipe to dismiss behaviour to items of this recycle view.
 * @param VH Type of viewHolder that will user will be able to swipe. Other viewHolders will not be swipeable.
 * @param foregroundView Lambda to retrieve foreground view from viewHolder. This view will be swiped, while other views in this viewHolder will stand still
 * @param onSwiped Callback executed when swipe gesture finishes
 */
inline fun <reified VH: RecyclerView.ViewHolder> RecyclerView.attachSwipeToDismiss(
    crossinline foregroundView: (VH) -> View,
    crossinline onSwiped: (VH) -> Unit
) {
    val itemTouchHelper = ItemTouchHelper(getItemTouchCallback(foregroundView, onSwiped))
    itemTouchHelper.attachToRecyclerView(this)
}

inline fun <reified VH> getItemTouchCallback(
    crossinline foregroundView: (VH) -> View,
    crossinline onSwiped: (VH) -> Unit
): ItemTouchHelper.SimpleCallback {
    return object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        override fun onMove(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onChildDraw(
            canvas: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            if (viewHolder !is VH) {
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            } else {
                getDefaultUIUtil().onDraw(
                    canvas,
                    recyclerView,
                    foregroundView(viewHolder),
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        override fun onChildDrawOver(
            canvas: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder?,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            if (viewHolder !is VH) {
                super.onChildDrawOver(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            } else {
                getDefaultUIUtil().onDrawOver(
                    canvas,
                    recyclerView,
                    foregroundView(viewHolder),
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        override fun clearView(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) {
            if (viewHolder !is VH) {
                super.clearView(recyclerView, viewHolder)
            } else {
                getDefaultUIUtil().clearView(foregroundView(viewHolder))
            }
        }

        override fun getSwipeDirs(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return if (viewHolder !is VH) 0
            else super.getSwipeDirs(recyclerView, viewHolder)
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (viewHolder !is VH) return
            if (direction != ItemTouchHelper.LEFT) return
            onSwiped(viewHolder)
        }
    }
}