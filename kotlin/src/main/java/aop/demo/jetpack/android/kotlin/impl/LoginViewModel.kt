package aop.demo.jetpack.android.kotlin.impl

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import aop.demo.jetpack.android.kotlin.R
import aop.demo.jetpack.android.kotlin.impl.repository.remote.LoginRepository
import aop.demo.jetpack.android.kotlin.impl.ui.ui.login.LoginFormState
import aop.demo.jetpack.android.kotlin.module_library.BaseViewModel

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    val loginFormState = MutableLiveData<LoginFormState>()
    var loginStatus = Transformations.map(loginRepository.login){
        it
    }

    var registerStauts = Transformations.map(loginRepository.register){it}
    var logoutStatus = Transformations.map(loginRepository.logout){it}

    fun login(userName: String, password: String) {
        val login = loginRepository.login(userName, password)
        addDisposable(login)
    }


    fun logout() {
        val logout = loginRepository.logout()
        addDisposable(logout)
    }


    fun register(username:String,password:String,repassword:String) {
        val register = loginRepository.register(username, password, repassword)
        addDisposable(register)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(LoginFormState(R.string.invalid_username, null))
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(LoginFormState(null, R.string.invalid_password))
        } else {
            loginFormState.setValue(LoginFormState(true))
        }
    }
    // A placeholder username validation check
    private fun isUserNameValid(username: String?): Boolean {
        if (username == null) {
            return false
        }
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.trim { it <= ' ' }.isNotEmpty()
        }

    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String?): Boolean {
        return password != null && password.trim { it <= ' ' }.length > 5
    }
}