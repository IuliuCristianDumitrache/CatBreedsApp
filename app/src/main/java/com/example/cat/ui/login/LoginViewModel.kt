package com.example.cat.ui.login

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cat.R
import com.example.cat.data.repository.LoginRepository
import com.example.cat.extensions.disposeIfNotAlready
import com.example.cat.extensions.isValidEmail
import com.example.cat.network.exceptions.NetworkExceptionHandler
import com.example.cat.utils.AlertDialogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val networkExceptionHandler: NetworkExceptionHandler,
) : ViewModel() {

    val loginInTheApp: MutableLiveData<Boolean> = MutableLiveData()
    val showProgress: MutableLiveData<Boolean> = MutableLiveData()
    val showError: MutableLiveData<String> = MutableLiveData()
    val showInvalidLogin: MutableLiveData<Int> = MutableLiveData()
    var loginDisposable: Disposable? = null

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            showInvalidLogin.postValue(R.string.invalid_credentials)
        } else if (!email.isValidEmail()) {
            showInvalidLogin.postValue(R.string.invalid_email)
        } else {
            showProgress.postValue(true)
            loginDisposable = loginRepository.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .doAfterTerminate { showProgress.postValue(false) }
                .subscribeBy(
                    onNext = {
                        loginInTheApp.postValue(true)
                    },
                    onError = {
                        showError.postValue(networkExceptionHandler.map(it).errorMessage)
                    }
                )
        }
    }

    override fun onCleared() {
        loginDisposable?.disposeIfNotAlready()
        super.onCleared()
    }
}