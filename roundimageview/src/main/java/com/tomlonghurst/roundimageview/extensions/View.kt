package com.tomlonghurst.roundimageview.extensions

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal fun View.beInvisibleIf(beInvisible: Boolean) = if (beInvisible) beInvisible() else beVisible()

internal fun View.beVisibleIf(beVisible: Boolean) = if (beVisible) beVisible() else beGone()

internal fun View.beGoneIf(beGone: Boolean) = beVisibleIf(!beGone)

internal fun View.beInvisible() {
    GlobalScope.launch(Dispatchers.Main) {
        visibility = View.INVISIBLE
    }
}

internal fun View.beVisible() {
    GlobalScope.launch(Dispatchers.Main) {
        visibility = View.VISIBLE
    }
}

internal fun View.beGone() {
    GlobalScope.launch(Dispatchers.Main) {
        visibility = View.GONE
    }
}

internal fun View.onGlobalLayout(callback: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            callback()
        }
    })
}

internal fun View.postOnMainThread(action: () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        action.invoke()
    }
}

internal suspend fun View.remove() = withContext(Dispatchers.Main) {
    (parent as ViewGroup).removeView(this@remove)
}

internal fun View.isVisible() = visibility == View.VISIBLE

internal fun View.isInvisible() = visibility == View.INVISIBLE

internal fun View.isGone() = visibility == View.GONE