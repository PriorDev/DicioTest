package com.prior.diciotest.feature_users.data.remote

import com.prior.diciotest.core.Resource
import com.prior.diciotest.data.remote.response.UserResponse

interface UsersService {
    suspend fun getUsers(): Resource<List<UserResponse>>
}