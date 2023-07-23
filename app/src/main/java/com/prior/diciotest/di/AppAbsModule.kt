package com.prior.diciotest.di

import com.prior.diciotest.data.SettingsRepositoryImp
import com.prior.diciotest.domain.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppAbsModule {
    @Binds
    @Singleton
    abstract fun providesSettingRepository(setting: SettingsRepositoryImp): SettingsRepository
}