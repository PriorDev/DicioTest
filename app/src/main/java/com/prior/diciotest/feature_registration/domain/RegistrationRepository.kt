package com.prior.diciotest.feature_registration.domain

import com.prior.diciotest.core.Resource
import com.prior.diciotest.feature_registration.domain.models.RegistrationModel
import com.prior.diciotest.domain.models.UserData
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    suspend fun insertUser(request: RegistrationModel): Flow<Resource<UserData>>
}