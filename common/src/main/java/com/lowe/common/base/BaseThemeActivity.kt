package com.lowe.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lowe.resource.theme.ThemeHelper
import kotlinx.coroutines.launch

open class BaseThemeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initConfigs(ActivityConfigHelper.getConfigs())
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            ActivityConfigHelper.collectConfigChange().collect {
                recreate()
            }
        }
    }

    private fun initConfigs(configs: Set<Config>) {
        configs.forEach {
            when (it) {
                is Config.ThemeConfig -> onThemeConfigChanged(it)
            }
        }
    }

    private fun onThemeConfigChanged(config: Config.ThemeConfig) {
        setTheme(ThemeHelper.getThemeRes(config.key))
    }

}