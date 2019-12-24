package aop.demo.jetpack.android.kotlin.impl.ui.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import aop.demo.jetpack.android.kotlin.R
import aop.demo.jetpack.android.kotlin.impl.LoginViewModel
import aop.demo.jetpack.android.kotlin.impl.repository.remote.LoginRepository
import aop.demo.jetpack.android.kotlin.module_library.util.PreferencesUtil

@Suppress("UNCHECKED_CAST")
class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText

    private lateinit var loadingProgressBar: ProgressBar

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginButton: Button
    private lateinit var passwordEditText: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initData()

//        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
//                .get(LoginViewModel::class.java)
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login)
        loadingProgressBar = findViewById(R.id.loading)
        loginButton.isEnabled = true

        /*  loginViewModel!!.loginFormState.observe(this, Observer { loginFormState ->
              if (loginFormState == null) {
                  return@Observer
              }
              loginButton.isEnabled = loginFormState.isDataValid
              if (loginFormState.usernameError != null) {
                  usernameEditText.error = getString(loginFormState.usernameError!!)
              }
              if (loginFormState.passwordError != null) {
                  passwordEditText.error = getString(loginFormState.passwordError!!)
              }
          })*/

//        loginViewModel!!.loginResult.observe(this, Observer { loginResult ->
//            if (loginResult == null) {
//                return@Observer
//            }
//            if (loginResult.error != null) {
//                showLoginFailed(loginResult.error)
//            }
//            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success!!)
//            }
//            setResult(Activity.RESULT_OK)
//
//            //Complete and destroy login activity once successful
//            finish()
//        })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(usernameEditText.text.toString(),
                        passwordEditText.text.toString())
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.text.toString(),
                        passwordEditText.text.toString())
            }
            return@setOnEditorActionListener false
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
//            loginViewModel.login(usernameEditText.text.toString(),
//                    passwordEditText.text.toString())

            loginViewModel.register(usernameEditText.text.toString(),passwordEditText.text
                    .toString(),passwordEditText.text
                    .toString())
        }
    }

    private fun initData() {


        loginViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return LoginViewModel(loginRepository = LoginRepository()) as T
            }

        })[LoginViewModel::class.java]
//        loginViewModel = ViewModelProviders.of(this@LoginActivity, object : ViewModelProvider
//        .Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                return LoginViewModel(LoginRepository()) as T
//            }
//
//        })[LoginViewModel::class.java]


        loginViewModel.apply {
            loginStatus.observe(this@LoginActivity, Observer {
                loadingProgressBar.visibility = View.GONE

                if (it?.errorCode == 0) {
                    //存储账号和cookie等信息
//                    ToastUtils.showToast("登录成功", Toast.LENGTH_LONG)
                    Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
                    var isLogin: Boolean by PreferencesUtil("login", false)
                    var userName: String by PreferencesUtil("userName", "Android")
                    var nikeName: String by PreferencesUtil("nikeName", "易水寒")
                    isLogin = true
                    userName = it.data?.username!!
                    nikeName = it.data?.nickname!!

//                    LiveEventBus.get()
//                            .with(LOGIN_SUCCESS)
//                            .post("登录成功")
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, it?.errorMsg, Toast.LENGTH_SHORT).show();
                }
            })

            registerStauts.observe(this@LoginActivity, Observer {
                loadingProgressBar.visibility = View.GONE
                if (it?.errorCode == 0) {

                    //存储账号和cookie等信息
//                    ToastUtils.showToast("登录成功", Toast.LENGTH_LONG)
                    Toast.makeText(this@LoginActivity, "登录成功", Toast.LENGTH_SHORT).show()
                    var isLogin: Boolean by PreferencesUtil("login", false)
                    var userName: String by PreferencesUtil("userName", "Android")
                    var nikeName: String by PreferencesUtil("nikeName", "易水寒")
                    isLogin = true
                    userName = it.data?.username!!
                    nikeName = it.data?.nickname!!

//                    LiveEventBus.get()
//                            .with(LOGIN_SUCCESS)
//                            .post("登录成功")
                    Toast.makeText(this@LoginActivity, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this@LoginActivity, "${it?.errorMsg}", Toast.LENGTH_SHORT).show();
                }
            })



            loginFormState.observe(this@LoginActivity, Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                if (loginFormState.usernameError != null) {
                    usernameEditText.error = getString(loginFormState.usernameError!!)
                }
                if (loginFormState.passwordError != null) {
                    passwordEditText.error = getString(loginFormState.passwordError!!)
                }
            })
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(applicationContext, welcome, Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int?) {
        Toast.makeText(applicationContext, errorString!!, Toast.LENGTH_SHORT).show()
    }
}
