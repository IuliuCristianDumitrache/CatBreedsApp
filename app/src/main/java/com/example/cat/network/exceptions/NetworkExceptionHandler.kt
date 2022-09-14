package com.example.cat.network.exceptions

import android.util.Log
import com.example.cat.R
import com.example.cat.models.ErrorModel
import com.example.cat.utils.StringResource
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkExceptionHandler @Inject constructor(private val resources: StringResource) {

    companion object {
        private val TAG = NetworkExceptionHandler::class.simpleName
    }

    fun map(throwable: Throwable): ErrorModel {
        Log.e(TAG, "", throwable)
        return when (throwable) {
            is HttpException -> mapHttpException(throwable)
            is NetworkNotAvailableException -> {
                ErrorModel(errorMessage = resources.getString(R.string.no_internet_error))
            }
            else -> {
                ErrorModel(errorMessage = resources.getString(R.string.something_went_wrong))
            }
        }
    }

    private fun mapHttpException(exception: HttpException): ErrorModel {
        return try {
            val errorBody = exception.response()?.errorBody()?.string()
            ErrorModel(
                errorMessage = errorBody ?: resources.getString(R.string.something_went_wrong),
                errorCode = exception.code()
            )
        } catch (parseException: Exception) {
            ErrorModel(
                errorMessage = resources.getString(R.string.something_went_wrong),
                errorCode = exception.code()
            )
        }
    }
}