package com.guowei.coordinatorlayoutexample

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton


class QuickHideBehavior : CoordinatorLayout.Behavior<View> {

    private var scrollingDirection: Int? = null

    private var scrollTrigger: Int? = null

    private var scrollDistance: Int = 0

    private var scrollThreshold: Int = 0

    private var animator: ObjectAnimator? = null

    //Required to instantiate as a default behavior
    constructor()

    //Required to attach behavior via XML
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val a = context.theme.obtainStyledAttributes(intArrayOf(R.attr.actionBarSize))
        scrollThreshold = a.getDimensionPixelSize(0, 0) / 2
        a.recycle()
    }

    /**
     * Called before a nested scroll event. Return true to declare interest
     */
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        //We have to declare interest in the scroll to receive further events
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    /**
     * Called before the scrolling child consumes the event
     * We can steal all/part of the event by filling in the consumed array
     */
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        // Determine direction changes here
        if (dy > 0 && scrollingDirection != DIRECTION_UP) {
            scrollingDirection = DIRECTION_UP
            scrollDistance = 0
        } else if (dy < 0 && scrollingDirection != DIRECTION_DOWN) {
            scrollingDirection = DIRECTION_DOWN
            scrollDistance = 0
        }
    }

    /**
     * Called after the scrolling child consumes the event, with amount consumed
     */
    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        //Consumed distance is the actual distance traveled by the scrolling view
        scrollDistance += dyConsumed
        if (scrollDistance > scrollThreshold && scrollTrigger != DIRECTION_UP) {
            scrollTrigger = DIRECTION_UP
            restartAnimator(child, getTargetHideValue(coordinatorLayout, child))
        } else if (scrollDistance < -scrollThreshold && scrollTrigger != DIRECTION_DOWN) {
            scrollTrigger = DIRECTION_DOWN
            restartAnimator(child, 0f)
        }
    }

    //Called after the scrolling child handles the fling
    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        //We only care when the target view is already handling the fling
        if (consumed) {
            if (velocityY > 0 && scrollTrigger != DIRECTION_UP) {
                scrollTrigger = DIRECTION_UP
                restartAnimator(child, getTargetHideValue(coordinatorLayout!!, child))
            } else if (velocityY < 0 && scrollTrigger != DIRECTION_DOWN) {
                scrollTrigger = DIRECTION_DOWN
                restartAnimator(child, 0f)
            }
        }
        return false
    }

    /* Helper Methods */

    //Helper to trigger hide/show animation
    private fun restartAnimator(target: View, value: Float) {
        if (animator != null) {
            animator!!.cancel()
            animator = null
        }
        animator = ObjectAnimator
            .ofFloat(target, View.TRANSLATION_Y, value)
            .setDuration(250)
        animator!!.start()
    }

    private fun getTargetHideValue(parent: ViewGroup, target: View): Float {
        if (target is AppBarLayout) {
            return (-target.getHeight()).toFloat()
        } else if (target is FloatingActionButton) {
            return (parent.height - target.getTop()).toFloat()
        }
        return 0f
    }

    companion object {
        private const val DIRECTION_UP = 1
        private const val DIRECTION_DOWN = -1
    }

}