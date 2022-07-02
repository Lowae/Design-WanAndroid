package com.lowe.wanandroid.ui.setting

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.lowe.wanandroid.BuildConfig
import com.lowe.wanandroid.R
import com.lowe.wanandroid.constant.SettingConstants
import com.lowe.wanandroid.ui.about.AboutActivity
import com.lowe.wanandroid.ui.web.WebActivity


class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, null)
        findPreference<SwitchPreference>(SettingConstants.PREFERENCE_KEY_NORMAL_CATEGORY_DYNAMIC_COLORS)?.apply {

        }
        findPreference<ListPreference>(SettingConstants.PREFERENCE_KEY_NORMAL_CATEGORY_DARK_MODE)?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                AppCompatDelegate.setDefaultNightMode(SettingConstants.getNightMode(newValue.toString()))
                true
            }
        }
        findPreference<Preference>(SettingConstants.PREFERENCE_KEY_OTHER_CATEGORY_ABOUT)?.apply {
            summary = getString(R.string.app_version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE.toString())
            setOnPreferenceClickListener {
                startActivity(Intent(this@SettingFragment.requireContext(), AboutActivity::class.java))
                true
            }
        }

        findPreference<Preference>(SettingConstants.PREFERENCE_KEY_OTHER_CATEGORY_GITHUB)?.apply {
            setOnPreferenceClickListener {
                WebActivity.loadUrl(requireContext(), summary.toString())
                true
            }
        }
    }
}