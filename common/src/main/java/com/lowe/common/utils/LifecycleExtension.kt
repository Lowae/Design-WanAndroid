package com.lowe.common.utils

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun ComponentActivity.launchRepeatOnCreated(crossinline block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            block()
        }
    }
}

inline fun ComponentActivity.launchRepeatOnStarted(crossinline block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

inline fun ComponentActivity.launchRepeatOnResumed(crossinline block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            block()
        }
    }
}

inline fun Fragment.launchRepeatOnCreated(crossinline block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            block()
        }
    }
}

inline fun Fragment.launchRepeatOnStarted(crossinline block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

inline fun Fragment.launchRepeatOnResumed(crossinline block: suspend CoroutineScope.() -> Unit) {
    lifecycle.coroutineScope.launch {
        repeatOnLifecycle(Lifecycle.State.RESUMED) {
            block()
        }
    }
}