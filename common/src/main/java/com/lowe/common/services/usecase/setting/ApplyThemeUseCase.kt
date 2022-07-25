package com.lowe.common.services.usecase.setting

import com.lowe.common.base.datastore.PreferenceStorage
import com.lowe.common.di.IoDispatcher
import com.lowe.common.services.usecase.UseCase
import com.lowe.resource.theme.ThemePrimaryKey
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ApplyThemeUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<ThemePrimaryKey, Unit>(dispatcher) {
    override suspend fun execute(parameters: ThemePrimaryKey) {
        preferenceStorage.applyTheme(parameters.storageKey)
    }
}