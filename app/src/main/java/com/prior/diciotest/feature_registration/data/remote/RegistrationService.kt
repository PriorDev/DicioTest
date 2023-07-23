package com.prior.diciotest.feature_registration.data.remote

import com.prior.diciotest.core.Resource
import com.prior.diciotest.feature_registration.domain.models.RegistrationModel
import com.prior.diciotest.data.remote.response.UserResponse

interface RegistrationService {
    suspend fun insertUser(request: RegistrationModel): Resource<UserResponse>
}