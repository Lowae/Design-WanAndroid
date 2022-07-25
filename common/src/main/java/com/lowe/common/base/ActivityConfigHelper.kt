package com.lowe.common.base

import android.util.Log
import com.lowe.resource.theme.ThemePrimaryKey
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged

object ActivityConfigHelper {

    private var configSets = setOf<Config>()

    private val configChangeSharedFlow = MutableSharedFlow<Set<Config>>(extraBufferCapacity = 1)

    fun collectConfigChange() =
        configChangeSharedFlow.asSharedFlow().distinctUntilChanged(this::areConfigsEquivalent)

    fun updateConfig(config: Config, notify: Boolean = true) {
        configSets = setOf(config)
        if (notify) {
            configChangeSharedFlow.tryEmit(configSets)
        }
    }

    fun updateConfig(config: Set<Config>, notify: Boolean = true) {
        configSets = config
        if (notify) {
            configChangeSharedFlow.tryEmit(configSets)
        }
    }

    fun getConfigs() = configSets.toSet()

    private fun areConfigsEquivalent(old: Set<Config>, new: Set<Config>): Boolean {
        return if (old.size != new.size) false else old.containsAll(new).apply {
            Log.d(
                "ActivityConfigHelper",
                "areConfigsEquivalent old: $old - new: $new - result: $this"
            )
        }
    }

}

sealed interface Config {

    data class ThemeConfig(val key: ThemePrimaryKey) : Config

}