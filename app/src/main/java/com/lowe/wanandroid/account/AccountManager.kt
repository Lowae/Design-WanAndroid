package com.lowe.wanandroid.account

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.base.http.DataStoreFactory
import com.lowe.wanandroid.base.http.RetrofitManager
import com.lowe.wanandroid.di.ApplicationCoroutineScope
import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.services.model.UserBaseInfo
import com.lowe.wanandroid.utils.fromJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

object AccountManager {

    private val PREFERENCE_KEY_ACCOUNT_USER_INFO = stringPreferencesKey("key_account_user_info")

    private val applicationScope: CoroutineScope =
        ApplicationCoroutineScope.providesIOCoroutineScope()
    private val dataStore: DataStore<Preferences> =
        DataStoreFactory.getDefaultPreferencesDataStore()

    private val userBaseInfoStateFlow: MutableStateFlow<UserBaseInfo> =
        MutableStateFlow(UserBaseInfo())

    private val accountStatusFlow: MutableStateFlow<AccountState> = MutableStateFlow(
        if (RetrofitManager.isLoginCookieValid()) AccountState.LogIn(true) else AccountState.LogOut
    )

    init {
        initUserDataFlow()
    }

    /**
     * 用户信息，基于dataStore
     */
    fun collectUserInfoFlow(): StateFlow<UserBaseInfo> = userBaseInfoStateFlow

    /**
     * 用户状态信息
     */
    fun accountStateFlow(): StateFlow<AccountState> = accountStatusFlow

    private fun initUserDataFlow() {
        applicationScope.launch {
            dataStore.data
                .catch {
                    AppLog.e(msg = "Error reading preferences.", exception = it)
                    emit(emptyPreferences())
                }.map {
                    AppLog.d(msg = "initUserDataFlow fromJson: ${it.asMap().keys.toString()} ")
                    if (it.contains(PREFERENCE_KEY_ACCOUNT_USER_INFO)) {
                        Gson().fromJson(it[PREFERENCE_KEY_ACCOUNT_USER_INFO]) ?: UserBaseInfo()
                    } else {
                        UserBaseInfo()
                    }
                }.collectLatest { userBaseInfo ->
                    AppLog.d(msg = "${PREFERENCE_KEY_ACCOUNT_USER_INFO.name} initUserDataFlow : ${userBaseInfo.userInfo}")
                    userBaseInfoStateFlow.emit(userBaseInfo)
                }
        }
    }

    fun cacheUserBaseInfo(userBaseInfo: UserBaseInfo) {
        applicationScope.launch {
            dataStore.edit {
                it[PREFERENCE_KEY_ACCOUNT_USER_INFO] = Gson().toJson(userBaseInfo).apply {
                    AppLog.d(msg = "${PREFERENCE_KEY_ACCOUNT_USER_INFO.name} cacheUserBaseInfo : $this}")
                }
            }
        }
    }

    fun clearUserBaseInfo() {
        applicationScope.launch {
            dataStore.edit {
                it[PREFERENCE_KEY_ACCOUNT_USER_INFO] = ""
                AppLog.d(msg = "${PREFERENCE_KEY_ACCOUNT_USER_INFO.name} clearUserBaseInfo")
            }
        }
    }

    fun peekUserBaseInfo(): UserBaseInfo = userBaseInfoStateFlow.value

    fun logIn(user: User) {
        applicationScope.launch {
            accountStatusFlow.emit(AccountState.LogIn(true, user))
        }
    }

    fun logout() {
        applicationScope.launch {
            clearUserBaseInfo()
            accountStatusFlow.emit(AccountState.LogOut)
        }
    }

    fun isMe(userId: String) = peekUserBaseInfo().userInfo.id == userId

    fun isLogin() = accountStatusFlow.value.isLogin
}