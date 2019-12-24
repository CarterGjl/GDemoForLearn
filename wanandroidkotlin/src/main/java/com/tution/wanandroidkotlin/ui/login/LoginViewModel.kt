package com.tution.wanandroidkotlin.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tution.wanandroidkotlin.base.BaseViewModel
import com.tution.wanandroidkotlin.bean.Result
import com.tution.wanandroidkotlin.bean.User
import com.tution.wanandroidkotlin.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : BaseViewModel() {


    val mRegisterUser: MutableLiveData<User> = MutableLiveData()
    private val _uiState = MutableLiveData<LoginUiModel>()

    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    private val repository by lazy {
        LoginRepository()
    }

    fun login(userName: String,passWord: String){
        viewModelScope.launch(Dispatchers.Default) {
            if (userName.isBlank() || passWord.isBlank()) return@launch

            withContext(Dispatchers.Main){
                showLoading()
            }

            val result = repository.login(userName,passWord)

            withContext(Dispatchers.Main){
                if (result is Result.Success) {
                    emitUiState(showSuccess = result.data,enableLoginButton = true)
                } else if (result is Result.Error) {
                    emitUiState(showError = result.exception.message,enableLoginButton = true)
                }
            }
        }
    }
    fun register(userName: String,passWord: String){
        viewModelScope.launch(Dispatchers.Default) {
            if (userName.isBlank()||passWord.isBlank()) return@launch
            withContext(Dispatchers.Main){
                showLoading()
            }
            val result = repository.register(userName, passWord)
            withContext(Dispatchers.Main){
                if (result is Result.Success){
                    emitUiState(showSuccess = result.data,enableLoginButton = true)
                }else if (result is Result.Error){
                    emitUiState(showError = result.exception.message,enableLoginButton = true)
                }
            }
        }
    }

    private fun isInputValid(userName: String, passWord: String): Boolean {
        return userName.isNotBlank() && passWord.isNotBlank()
    }

    fun loginDataChanged(userName: String, passWord: String) {
        emitUiState(enableLoginButton = isInputValid(userName, passWord))
    }

    private fun showLoading(){
        emitUiState(showProgress = true)
    }
    private fun emitUiState(showProgress: Boolean = false, showError: String? = null, showSuccess:
    User? = null, enableLoginButton: Boolean = false, needLogin: Boolean = false) {
        val uiModel = LoginUiModel(showProgress, showError, showSuccess, enableLoginButton, needLogin)
        _uiState.value = uiModel
    }

    data class LoginUiModel(
            val showProgress: Boolean,
            val showError: String?,
            val showSuccess: User?,
            val enableLoginButton: Boolean,
            val needLogin: Boolean
    )


}