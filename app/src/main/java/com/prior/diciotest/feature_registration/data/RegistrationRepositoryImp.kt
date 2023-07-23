package com.prior.diciotest.feature_registration.data

import android.util.Log
import com.prior.diciotest.R
import com.prior.diciotest.core.Resource
import com.prior.diciotest.core.UiMessages
import com.prior.diciotest.feature_registration.domain.models.RegistrationModel
import com.prior.diciotest.domain.models.UserData
import com.prior.diciotest.domain.models.toDomain
import com.prior.diciotest.feature_registration.data.remote.RegistrationService
import com.prior.diciotest.feature_registration.domain.RegistrationRepository
import com.prior.diciotest.feature_users.data.UsersRepositoryImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class RegistrationRepositoryImp @Inject constructor(
    private val service: RegistrationService
): RegistrationRepository {
    override suspend fun insertUser(request: RegistrationModel): Flow<Resource<UserData>> {
        return flow {
            try{
                emit(Resource.Loading())

                val userResponse = service.insertUser(request)

                when(userResponse){
                    is Resource.Error -> throw Exception()
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        if(userResponse.data == null){
                            throw Exception()
                        }

                        emit(Resource.Success(userResponse.data.toDomain()))
                    }
                }
            }catch (e: Exception){
                Log.e(UsersRepositoryImp.TAG, "insertUser: ${e.message}")
                emit(Resource.Error(
                    UiMessages.StringResource(R.string.fail_to_register))
                )
                return@flow
            }finally {
                emit(Resource.Loading(false))
            }
        }
    }
}