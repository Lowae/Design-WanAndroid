package com.lowe.common.theme

import com.lowe.common.di.ApplicationScope
import com.lowe.common.services.usecase.setting.ApplyThemeUseCase
import com.lowe.common.services.usecase.setting.ThemeChangeFlowUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ThemeViewModelDelegateModule {

    @Singleton
    @Provides
    fun provideAccountViewModelDelegate(
        @ApplicationScope applicationScope: CoroutineScope,
        themeChangeFlowUseCase: ThemeChangeFlowUseCase,
        applyThemeUseCase: ApplyThemeUseCase,
    ): ThemeViewModelDelegate =
        ThemeActivityDelegateImpl(
            applicationScope,
            themeChangeFlowUseCase,
            applyThemeUseCase
        )
}