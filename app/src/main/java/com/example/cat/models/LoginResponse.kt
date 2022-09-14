package com.example.cat.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse (
    var accessToken: String,
    var expiresIn: Long? = null,
    var refreshToken: String? = null,
    var refreshTokenExpiresIn: String? = null,
    var tokenType: String? = null,
    var scope: String? = null
) : Parcelable