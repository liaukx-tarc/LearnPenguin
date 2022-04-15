package bigboss.team.learnpenguin.Common

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import bigboss.team.learnpenguin.R

abstract class SwipeToAddFunction(context: Context): ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT ){

    private val swipeIcon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_star_72)
    private val intrinsicWidth = swipeIcon!!.intrinsicWidth
    private val intrinsicHeight = swipeIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#FFFF99")
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
            clearCanvas(c, itemView.left + dX, itemView.top.toFloat(), itemView.left.toFloat(), itemView.bottom.toFloat())
            if (recyclerView != null) {
                if (c != null) {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
            return
        }

        // Draw background
        background.color = backgroundColor
        background.setBounds(itemView.left + dX.toInt(), itemView.top, itemView.left, itemView.bottom)
        if (c != null) {
            background.draw(c)
        }

        // Calculate position of icon
        val swipeIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val swipeIconMargin = (itemHeight - intrinsicHeight) / 2
        val swipeIconLeft = itemView.left + swipeIconMargin / 2
        val swipeIconRight = itemView.left + swipeIconMargin / 2 + intrinsicWidth
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