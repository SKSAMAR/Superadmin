package com.fintech.paytoindia.clean.data.repository

import com.fintech.paytoindia.clean.data.remote.YesApi
import com.fintech.paytoindia.clean.domain.repository.YesRepository
import com.fintech.paytoindia.data.db.dao.AuthDao
import com.fintech.paytoindia.data.db.dao.ProfileDao
import com.fintech.paytoindia.data.db.dao.UserDao
import com.fintech.paytoindia.data.db.entities.AuthData
import com.fintech.paytoindia.data.db.entities.User
import com.fintech.paytoindia.data.db.entities.UserProfile
import com.fintech.paytoindia.data.network.responses.RegularResponse
import com.fintech.paytoindia.data.network.responses.SystemResponse
import com.fintech.paytoindia.data_model.LoginModel
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