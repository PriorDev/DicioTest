package com.prior.diciotest.feature_users.data.remote

import android.util.Log
import com.prior.diciotest.core.RoutesHttp
import com.prior.diciotest.core.Resource
import com.prior.diciotest.core.UiMessages
import com.prior.diciotest.data.remote.response.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersServiceImp @Inject constructor(
    private val client: HttpClient
): UsersService {
    override suspend fun getUsers(): Resource<List<UserResponse>> {
        return withContext(Dispatchers.IO){
            try {
                val result = client.get<List<UserResponse>>{
                    url(RoutesHttp.GET_USERS)
                    contentType(ContentType.Application.Json)

                    header("Content-Type", "application/json")
                    header("Host", "api.devdicio.net")
                    header("xc-token", "J38b4XQNLErVatKIh4oP1jw9e_wYWkS86Y04TMNP")
                }

                Resource.Success(result)
            }catch (e: Exception){
                Log.e(TAG, "getUsers: ${e.message} \n ${e.printStackTrace()}")
                Resource.Error(UiMessages.DynamicMessage(e.message ?: ""))
            }
        }
    }

    companion object{
        const val TAG = "UserServiceImp"
    }
}