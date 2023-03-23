package com.fintech.payware.clean.data.repository

import com.fintech.payware.clean.data.remote.YesApi
import com.fintech.payware.clean.domain.repository.YesRepository
import com.fintech.payware.data.db.dao.AuthDao
import com.fintech.payware.data.db.dao.ProfileDao
import com.fintech.payware.data.db.dao.UserDao
import com.fintech.payware.data.db.entities.AuthData
import com.fintech.payware.data.db.entities.User
import com.fintech.payware.data.db.entities.UserProfile
import com.fintech.payware.data.network.responses.RegularResponse
import com.fintech.payware.data.network.responses.SystemResponse
import com.fintech.payware.data_model.LoginModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YesRepositoryImp
@Inject constructor(
    private val api: YesApi,
    private val authDao: AuthDao,
    private val userDao: UserDao,
    private val profileDao: ProfileDao
): YesRepository {

    override suspend fun loginAbility(mobile: String): RegularResponse {
        return api.loginAbility(mobile)
    }

    override suspend fun getUserLogin(mobile: String, password: String): AuthData {
        return api.getUserLogin(mobile, password)
    }

    override suspend fun getAllCredentials(fetch_all: String): SystemResponse<LoginModel> {
        return api.getAllCredentials("fetch_all")
    }

    override suspend fun insertAuthData(authData: AuthData): Long {
        return authDao.insertAuthDao(authData)
    }

    override suspend fun insertUser(user: User): Long {
        return userDao.insert(user)
    }

    override suspend fun insertUserProfile(userProfile: UserProfile): Long {
        return profileDao.insertUserProfile(userProfile)
    }

}