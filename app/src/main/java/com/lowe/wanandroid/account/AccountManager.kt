package com.lowe.wanandroid.account

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.base.http.cookie.UserCookieJarImpl
import com.lowe.wanandroid.di.ApplicationScope
import com.lowe.wanandroid.di.IoDispatcher
import com.lowe.wanandroid.services.model.User
import com.lowe.wanandroid.services.model.UserBaseInfo
import com.lowe.wanandroid.utils.fromJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountManager @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val cookieJar: UserCookieJarImpl,
    @ApplicationScope private val applicationScope: CoroutineScope,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    companion object {
        private val PREFERENCE_KEY_ACCOUNT_USER_INFO = stringPreferencesKey("key_account_user_info")
    }

    private val userBaseInfoStateFlow: MutableStateFlow<UserBaseInfo> =
        MutableStateFlow(UserBaseInfo())

    private val accountStatusFlow: MutableStateFlow<AccountState> =
        MutableStateFlow(AccountState.LogOut)

    init {
        cookieJar.onCookieLoaded = {
            if (cookieJar.isLoginCookieValid()) {
                accountStatusFlow.tryEmit(AccountState.LogIn(true))
            }
        }
        applicationScope.launch(dispatcher) {
            dataStore.data
                .catch {
                    AppLog.e(msg = "Error reading preferences.", throwable = it)
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

    /**
     * 用户信息，基于dataStore
     */
    fun collectUserInfoFlow(): StateFlow<UserBaseInfo> = userBaseInfoStateFlow

    /**
     * 用户状态信息
     */
    fun accountStateFlow(): StateFlow<AccountState> = accountStatusFlow

    fun cacheUserBaseInfo(userBaseInfo: UserBaseInfo) {
        applicationScope.launch(dispatcher) {
            dataStore.edit {
                it[PREFERENCE_KEY_ACCOUNT_USER_INFO] = Gson().toJson(userBaseInfo).apply {
                    AppLog.d(msg = "${PREFERENCE_KEY_ACCOUNT_USER_INFO.name} cacheUserBaseInfo : $this}")
                }
            }
        }
    }

    fun clearUserBaseInfo() {
        applicationScope.launch(dispatcher) {
            dataStore.edit {
                it[PREFERENCE_KEY_ACCOUNT_USER_INFO] = ""
                AppLog.d(msg = "${PREFERENCE_KEY_ACCOUNT_USER_INFO.name} clearUserBaseInfo")
            }
        }
    }

    fun peekUserBaseInfo(): UserBaseInfo = userBaseInfoStateFlow.value

    fun logIn(user: User) {
        applicationScope.launch(dispatcher) {
            accountStatusFlow.emit(AccountState.LogIn(true, user))
        }
    }

    fun logout() {
        applicationScope.launch(dispatcher) {
            clearUserBaseInfo()
            cookieJar.clear()
            accountStatusFlow.emit(AccountState.LogOut)
        }
    }

    fun isMe(userId: String) = peekUserBaseInfo().userInfo.id == userId

    fun isLogin() = accountStatusFlow.value.isLogin
}