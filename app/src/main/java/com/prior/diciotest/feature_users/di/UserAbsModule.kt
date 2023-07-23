package com.prior.diciotest.feature_users.di

import com.prior.diciotest.feature_users.data.UsersRepositoryImp
import com.prior.diciotest.feature_users.data.remote.UsersServiceImp
import com.prior.diciotest.feature_users.data.remote.UsersService
import com.prior.diciotest.feature_users.domain.UsersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserAbsModule {
    @ViewModelScoped
    @Binds
    abstract fun providesUsersService(service: UsersServiceImp): UsersService

    @ViewModelScoped
    @Binds
    abstract fun providesUsersRepository(repository: UsersRepositoryImp) : UsersRepository
}