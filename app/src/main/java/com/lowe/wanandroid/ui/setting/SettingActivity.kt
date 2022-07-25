package com.lowe.wanandroid.ui.setting

import android.os.Bundle
import com.lowe.common.base.BaseThemeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseThemeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(android.R.id.content, SettingFragment())
                .commit()
        }
    }

}