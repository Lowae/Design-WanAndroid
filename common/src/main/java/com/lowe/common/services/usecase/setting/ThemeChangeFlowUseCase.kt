package com.lowe.common.services.usecase.setting

import com.lowe.common.base.datastore.PreferenceStorage
import com.lowe.common.di.DefaultDispatcher
import com.lowe.common.result.Result
import com.lowe.common.services.usecase.FlowUseCase
import com.lowe.resource.theme.ThemePrimaryKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeChangeFlowUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @DefaultDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, ThemePrimaryKey>(dispatcher) {
    override fun execute(parameters: Unit): Flow<Result<ThemePrimaryKey>> {
        return preferenceStorage.appliedTheme.map {
            Result.Success(it)
        }
    }
}