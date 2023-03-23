package com.fintech.webspidysoftware.clean.domain.use_cases.authUseCase

import com.fintech.webspidysoftware.clean.common.Resource
import com.fintech.webspidysoftware.clean.domain.repository.YesRepository
import com.fintech.webspidysoftware.data.db.entities.AuthData
import com.fintech.webspidysoftware.data.network.responses.RegularResponse
import com.fintech.webspidysoftware.data.network.responses.SystemResponse
import com.fintech.webspidysoftware.data_model.LoginModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AuthUseCase
@Inject constructor(private val yesRepository: YesRepository){

    fun loginAbility(mobile: String): Flow<Resource<RegularResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = yesRepository.loginAbility(mobile)
            emit(Resource.Success(response))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(e.localizedMessage?:"Couldn't reach server. Check your internet connection."))
        } catch(e: IllegalArgumentException) {
            emit(Resource.Error(e.localizedMessage?:"An unexpected error occurred"))
        }
    }


    fun getUserLogin(mobile: String, password: String): Flow<Resource<AuthData>> = flow {
        try {
            emit(Resource.Loading())
            val response = yesRepository.getUserLogin(mobile, password)
            if(response.status && response.rs_code == "200"){
                insertAuth(response)
                emit(Resource.Success(response))
            }
            else{
                emit(Resource.Error(response.user_Exist ?: "Failed"))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(e.localizedMessage?:"Couldn't reach server. Check your internet connection."))
        } catch(e: IllegalArgumentException) {
            emit(Resource.Error(e.localizedMessage?:"An unexpected error occurred"))
        }
    }

    fun getAllCredentials(): Flow<Resource<SystemResponse<LoginModel>>> = flow{
        try {
            emit(Resource.Loading())
            val response = yesRepository.getAllCredentials("fetch_all")
            if(response.status && response.response_code == 1){
                insertLoginModel(response.receivableData)
                emit(Resource.Success(response))
            }
            else{
                emit(Resource.Error(response.message))
            }
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Resource.Error(e.localizedMessage?:"Couldn't reach server. Check your internet connection."))
        } catch(e: IllegalArgumentException) {
            emit(Resource.Error(e.localizedMessage?:"An unexpected error occurred"))
        }
    }


    private suspend fun insertAuth(authData: AuthData){
        yesRepository.insertAuthData(authData)
    }

    private suspend fun insertLoginModel(loginModel: LoginModel){
        yesRepository.insertUser(loginModel.user)
        loginModel.userProfile?.let {
            yesRepository.insertUserProfile(loginModel.userProfile)
        }
    }
}