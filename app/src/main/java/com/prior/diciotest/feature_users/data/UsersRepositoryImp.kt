package com.prior.diciotest.feature_users.data

import android.util.Log
import com.prior.diciotest.R
import com.prior.diciotest.core.Resource
import com.prior.diciotest.core.UiMessages
import com.prior.diciotest.feature_users.data.database.InformationDao
import com.prior.diciotest.feature_users.data.database.UsersDao
import com.prior.diciotest.feature_users.data.database.toDB
import com.prior.diciotest.feature_users.data.remote.UsersService
import com.prior.diciotest.feature_users.domain.UsersRepository
import com.prior.diciotest.domain.models.UserData
import com.prior.diciotest.domain.models.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class UsersRepositoryImp @Inject constructor(
    private val service: UsersService,
    private val userDao: UsersDao,
    private val infoDao: InformationDao,
): UsersRepository {
    override suspend fun getUsers(): Flow<Resource<List<UserData>>> {
        return flow{
            try{
                emit(Resource.Loading())

                val userResponse = service.getUsers()

                when(userResponse){
                    is Resource.Error -> throw Exception()
                    is Resource.Loading -> {}
                    is Resource.Success -> {

                        if(userResponse.data == null){
                            val usersEntity = userDao.getUsers()

                            if(usersEntity.isNotEmpty()){
                                emit(Resource.Success(usersEntity.map { it.user.toDomain(it.information) }))
                                emit(Resource.Loading(false))
                                return@flow
                            }else{
                                throw Exception()
                            }
                        }

                        userResponse.data.map { user ->

                            userDao.insertUser(user.toDB())
                            user.datos?.let { datos ->
                                infoDao.insertInformation(datos.toDB(user.id, ""))
                            }
                        }

                        val user = userDao.getUsers()
                        emit(Resource.Success(userResponse.data.map { it.toDomain() }))
                    }
                }

            }catch (e: Exception){
                Log.e(TAG, "getUsers: ${e.message}")
                emit(Resource.Error(
                    UiMessages.StringResource(R.string.fail_to_get_information_from_remote_source))
                )
                return@flow
            }finally {
                emit(Resource.Loading(false))
            }
        }
    }

    companion object{
        const val TAG = "UsersRepositoryImp"
    }
}