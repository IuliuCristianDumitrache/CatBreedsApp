package com.example.cat.data.repository

import com.example.cat.models.LoginResponse
import io.reactivex.rxjava3.core.Observable

interface LoginRepository {
    fun loginUser(email: String, password: String): Observable<LoginResponse>
}