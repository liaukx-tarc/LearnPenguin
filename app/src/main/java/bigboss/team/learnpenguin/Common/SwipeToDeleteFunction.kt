package bigboss.team.learnpenguin.Common

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import bigboss.team.learnpenguin.R

abstract class SwipeToDeleteFunction(context: Context): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT ){

    private val swipeIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_forever_24)
    private val intrinsicWidth = swipeIcon!!.intrinsicWidth
    private val intrinsicHeight = swipeIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#F44336")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            if (recyclerView != null) {
                if (c != null) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
            return
        }

        // Draw background
        background.color = backgroundColor
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        if (c != null) {
            background.draw(c)
        }

        // Calculate position of icon
        val swipeIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val swipeIconMargin = (itemHeight - intrinsicHeight) / 2
        val swipeIconLeft = itemView.right - swipeIconMargin - intrinsicWidth
        val swipeIconRight = itemView.right - swipeIconMargin
        val swipeIconBottom = swipeIconTop + intrinsicHeight

        // Draw the icon
        swipeIcon!!.setBounds(swipeIconLeft, swipeIconTop, swipeIconRight, swipeIconBottom)
        if (c != null) {
            swipeIcon.draw(c)
        }

        if (recyclerView != null) {
            if (c != null) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }
}