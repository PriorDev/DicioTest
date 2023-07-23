package com.prior.diciotest.feature_registration.data.remote

import com.prior.diciotest.core.Resource
import com.prior.diciotest.core.RoutesHttp
import com.prior.diciotest.feature_registration.domain.models.RegistrationModel
import com.prior.diciotest.data.remote.response.UserResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegistrationServiceImp @Inject constructor(
    private val client: HttpClient
): RegistrationService {
    override suspend fun insertUser(request: RegistrationModel): Resource<UserResponse> {
        return withContext(Dispatchers.IO){
            val result = client.post<UserResponse>{
                url(RoutesHttp.GET_USERS)
                contentType(ContentType.Application.Json)

                header("Content-Type", "application/json")
                header("Host", "api.devdicio.net")
                header("xc-token", "J38b4XQNLErVatKIh4oP1jw9e_wYWkS86Y04TMNP")

                body = request
            }

            Resource.Success(result)
        }
    }

}