package com.prior.diciotest.feature_registration.di

import com.prior.diciotest.feature_registration.data.RegistrationRepositoryImp
import com.prior.diciotest.feature_registration.data.remote.RegistrationService
import com.prior.diciotest.feature_registration.data.remote.RegistrationServiceImp
import com.prior.diciotest.feature_registration.domain.RegistrationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RegistrationAbsModule {
    @Binds
    @ViewModelScoped
    abstract fun providesRegistrationService(service: RegistrationServiceImp): RegistrationService

    @Binds
    @ViewModelScoped
    abstract fun providesRegistrationRepository(repository: RegistrationRepositoryImp): RegistrationRepository
}