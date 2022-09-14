package com.example.cat.data.repository

import com.example.cat.data.SessionManager
import com.example.cat.data.remotedatasource.LoginRemoteDataSource
import com.example.cat.models.LoginResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val sessionManager: SessionManager
) : LoginRepository {
    override fun loginUser(email: String, password: String): Observable<LoginResponse> =
        loginRemoteDataSource.loginUser(email, password).doOnNext {
            sessionManager.setAccessToken(it.accessToken)
        }
}