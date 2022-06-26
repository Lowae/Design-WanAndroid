package com.lowe.wanandroid.account

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.base.http.DataStoreFactory
import com.lowe.wanandroid.di.ApplicationCoroutineScope
import com.lowe.wanandroid.services.model.UserBaseInfo
import com.lowe.wanandroid.utils.fromJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException

object AccountManager {

    private val PREFERENCE_KEY_ACCOUNT_USER_INFO = stringPreferencesKey("key_account_user_info")

    private val applicationScope: CoroutineScope =
        ApplicationCoroutineScope.providesIOCoroutineScope()
    private val dataStore: DataStore<Preferences> =
        DataStoreFactory.getDefaultPreferencesDataStore()

    private val userBaseInfoStateFlow: MutableStateFlow<UserBaseInfo> =
        MutableStateFlow(UserBaseInfo())

    init {
        initUserDataFlow()
    }

    private fun initUserDataFlow() {
        applicationScope.launch {
            dataStore.data
                .catch {
                    if (it is IOException) {
                        AppLog.e(msg = "Error reading preferences.", exception = it)
                        emit(emptyPreferences())
                    } else {
                        throw it
                    }
                }.map {
                    if (it.contains(PREFERENCE_KEY_ACCOUNT_USER_INFO)) {
                        Gson().fromJson<UserBaseInfo>(it[PREFERENCE_KEY_ACCOUNT_USER_INFO])
                    } else {
                        null
                    }
                }.collectLatest { userBaseInfo ->
                    AppLog.d(msg = "${PREFERENCE_KEY_ACCOUNT_USER_INFO.name}: ${userBaseInfo?.userInfo?.nickname}")
                    userBaseInfo?.apply { userBaseInfoStateFlow.emit(this) }
                }
        }
    }

    fun collectUserInfoFlow() = userBaseInfoStateFlow

    fun cacheUserBaseInfo(userBaseInfo: UserBaseInfo) {
        applicationScope.launch {
            dataStore.edit {
                it[PREFERENCE_KEY_ACCOUNT_USER_INFO] = Gson().toJson(userBaseInfo).apply {
                    AppLog.d(msg = "cacheUserBaseInfo: $this")
                }
            }
        }
    }

    fun peekUserBaseInfo(): UserBaseInfo = userBaseInfoStateFlow.value

    fun isMe(userId: String) = peekUserBaseInfo().userInfo.id == userId.toString()
}