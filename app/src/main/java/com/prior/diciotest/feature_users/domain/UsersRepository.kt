package com.prior.diciotest.feature_users.domain

import com.prior.diciotest.core.Resource
import com.prior.diciotest.domain.models.UserData
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun getUsers(): Flow<Resource<List<UserData>>>
}