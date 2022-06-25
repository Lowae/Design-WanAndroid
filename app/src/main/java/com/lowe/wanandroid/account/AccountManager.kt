package com.lowe.wanandroid.account

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.lowe.wanandroid.base.AppLog
import com.lowe.wanandroid.di.IOApplicationScope
import com.lowe.wanandroid.services.model.UserBaseInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class AccountManager @Inject constructor(
    @IOApplicationScope private val applicationScope: CoroutineScope,
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val PREFERENCE_KEY_ACCOUNT_LOCAL_USER_INFO =
            stringPreferencesKey("key_account_local_user_info")
        val PREFERENCE_KEY_ACCOUNT_SERVER_USER_INFO =
            stringPreferencesKey("key_account_server_user_info")
    }

    private val _accountStateFlow: MutableStateFlow<AccountState> =
        MutableStateFlow(AccountState.LogOut)
    val accountStateFlow: StateFlow<AccountState> = _accountStateFlow

    private val _userBaseInfoStateFlow = MutableStateFlow(UserBaseInfo())
    val userBaseInfoStateFlow: StateFlow<UserBaseInfo> = _userBaseInfoStateFlow

    fun init() {
        applicationScope.launch {
            dataStore.data
                .catch {
                    _accountStateFlow.emit(AccountState.LogOut)
                    if (it is IOException) {
                        AppLog.e(msg = "Error reading preferences.", exception = it)
                        emit(emptyPreferences())
                    } else {
                        throw it
                    }
                }.map {
                    if (it.contains(PREFERENCE_KEY_ACCOUNT_LOCAL_USER_INFO)) {
                        Gson().fromJson(
                            it[PREFERENCE_KEY_ACCOUNT_LOCAL_USER_INFO],
                            LocalUserInfo::class.java
                        )
                    } else {
                        LocalUserInfo()
                    }
                }.collectLatest {
                    AppLog.d(msg = "${PREFERENCE_KEY_ACCOUNT_LOCAL_USER_INFO.name}: ${it.username}")
                    if (it.isValid()) _accountStateFlow.emit(AccountState.LogIn(it))
                    else _accountStateFlow.emit(AccountState.LogOut)
                }
        }

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
                    if (it.contains(PREFERENCE_KEY_ACCOUNT_SERVER_USER_INFO)) {
                        Gson().fromJson(
                            it[PREFERENCE_KEY_ACCOUNT_SERVER_USER_INFO],
                            UserBaseInfo::class.java
                        )
                    } else {
                        null
                    }
                }.collectLatest { userBaseInfo ->
                    AppLog.d(msg = "${PREFERENCE_KEY_ACCOUNT_SERVER_USER_INFO.name}: ${userBaseInfo?.userInfo?.nickname}")
                    userBaseInfo?.apply { _userBaseInfoStateFlow.emit(this) }
                }
        }
    }

    fun cacheLocalUser(userInfo: LocalUserInfo) {
        applicationScope.launch {
            dataStore.edit {
                it[PREFERENCE_KEY_ACCOUNT_LOCAL_USER_INFO] = Gson().toJson(userInfo).apply {
                    AppLog.d(msg = "cacheLocalUser: $this")
                }
            }
        }
    }

    fun cacheServeUserInfo(userBaseInfo: UserBaseInfo) {
        applicationScope.launch {
            dataStore.edit {
                it[PREFERENCE_KEY_ACCOUNT_SERVER_USER_INFO] = Gson().toJson(userBaseInfo).apply {
                    AppLog.d(msg = "cacheServeUserInfo: $this")
                }
            }
        }
    }

    fun isLogin() = _accountStateFlow.value != AccountState.LogOut

    init {
        init()
    }

}