package com.lowe.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.collectAsStateWithLifecycle(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<T> = collectAsStateWithLifecycle(
    initialValue = remember { this.value },
    lifecycle = lifecycle,
    minActiveState = minActiveState
)

@Composable
fun <T> Flow<T>.collectAsStateWithLifecycle(
    initialValue: T,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): State<T> {
    val currentValue = remember(this) { initialValue }
    return produceState(
        initialValue = currentValue,
        key1 = this,
        key2 = lifecycle,
        key3 = minActiveState
    ) {
        lifecycle.repeatOnLifecycle(minActiveState) {
            this@collectAsStateWithLifecycle.collect {
                this@produceState.value = it
            }
        }
    }
}
