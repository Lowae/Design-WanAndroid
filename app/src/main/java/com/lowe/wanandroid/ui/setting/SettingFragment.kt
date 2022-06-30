package com.lowe.wanandroid.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.lowe.wanandroid.BuildConfig
import com.lowe.wanandroid.R
import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.constant.SettingConstants
import com.lowe.wanandroid.ui.web.WebActivity


class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, null)
        findPreference<ListPreference>(SettingConstants.PREFERENCE_KEY_NORMAL_CATEGORY_DARK_MODE)?.apply {
            setOnPreferenceChangeListener { _, newValue ->
                AppCompatDelegate.setDefaultNightMode(SettingConstants.getNightMode(newValue.toString()))
                true
            }
        }

        findPreference<Preference>(SettingConstants.PREFERENCE_KEY_OTHER_CATEGORY_ABOUT)?.apply {
            summary = "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})"
        }

        findPreference<Preference>(SettingConstants.PREFERENCE_KEY_OTHER_CATEGORY_HOME_WEBSITE)?.apply {
            summary = RetrofitManager.BASE_URL
            setOnPreferenceClickListener {
                WebActivity.loadUrl(requireContext(), RetrofitManager.BASE_URL)
                true
            }
        }
    }
}