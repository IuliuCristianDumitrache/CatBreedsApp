package com.example.cat.data.remotedatasource

import com.example.cat.models.LoginResponse
import com.example.cat.network.ApiService
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    //Server is not ready, mock login API
    fun loginUser(email: String, password: String): Observable<LoginResponse> =
        Observable.fromCallable {
                return@fromCallable LoginResponse(accessToken = "Bearer tmngjfsf2e3Fn")
            }.delay(1, TimeUnit.SECONDS)
}