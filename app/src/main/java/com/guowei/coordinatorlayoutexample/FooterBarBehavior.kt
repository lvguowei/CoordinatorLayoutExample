package com.guowei.coordinatorlayoutexample

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class FooterBarBehavior : CoordinatorLayout.Behavior<FooterBarLayout> {
    constructor()
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: FooterBarLayout,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: FooterBarLayout,
        dependency: View
    ): Boolean {
        val offset = -dependency.top
        child.translationY = offset.toFloat()
        return true
    }
}