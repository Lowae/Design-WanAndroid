package com.lowe.wanandroid.utils

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ComponentActivity.repeatOnCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED, block)
    }
}

fun ComponentActivity.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

fun ComponentActivity.repeatOnResumed(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED, block)
    }
}

fun Fragment.repeatOnCreated(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED, block)
    }
}

fun Fragment.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

fun Fragment.repeatOnResumed(block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED, block)
    }
}