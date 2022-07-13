package com.lowe.wanandroid.utils

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ComponentActivity.launchRepeatOnCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED, block)
    }
}

fun ComponentActivity.launchRepeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

fun ComponentActivity.launchRepeatOnResumed(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED, block)
    }
}

fun Fragment.launchRepeatOnCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED, block)
    }
}

fun Fragment.launchRepeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

fun Fragment.launchRepeatOnResumed(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED, block)
    }
}