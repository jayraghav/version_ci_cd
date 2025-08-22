package com.example.versionci_cd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // Simple validation logic
        if (username.isBlank() || password.isBlank()) {
            _loginResult.value = LoginResult.Error("Username or password cannot be empty")
            return
        }

        // Dummy credentials check
        if (username == "admin" && password == "1234") {
            _loginResult.postValue(LoginResult.Success)
        } else {
            _loginResult.postValue(LoginResult.Error("Invalid credentials"))
        }
    }

    // Result sealed class
    sealed class LoginResult {
        object Success : LoginResult()
        data class Error(val message: String) : LoginResult()
    }
}