package com.prior.diciotest.feature_users.di

import com.prior.diciotest.data.database.DicioDatabase
import com.prior.diciotest.feature_users.data.database.InformationDao
import com.prior.diciotest.feature_users.data.database.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UserModule {
    @ViewModelScoped
    @Provides
    fun providesUserDao(database: DicioDatabase): UsersDao{
        return database.userDao
    }

    @ViewModelScoped
    @Provides
    fun providesInformationDao(database: DicioDatabase): InformationDao{
        return database.infoDao
    }
}