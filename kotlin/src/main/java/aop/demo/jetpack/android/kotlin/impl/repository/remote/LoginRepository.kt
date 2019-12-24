package aop.demo.jetpack.android.kotlin.impl.repository.remote

import androidx.lifecycle.MutableLiveData
import aop.demo.jetpack.android.kotlin.impl.repository.model.HttpResponse
import aop.demo.jetpack.android.kotlin.impl.repository.model.LoginBean
import aop.demo.jetpack.android.kotlin.module_library.helper.RxHelper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginRepository {

    val login =  MutableLiveData<HttpResponse<LoginBean>>()

    val register = MutableLiveData<HttpResponse<LoginBean>>()

    val logout = MutableLiveData<HttpResponse<Any>>()

    fun login(account: String, password: String): Disposable {

        return RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL)
                .login(account, password)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({
                    login.value = it
                }, {
                    login.value = HttpResponse(null, 500, it.message)
                })
    }

    fun loginC(account: String,password: String){


        GlobalScope.launch(Dispatchers.Main) {
            val retrofit = Retrofit.Builder()
                    .baseUrl("")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
            val httpResponse = withContext(Dispatchers.IO) {
                retrofit.create(API::class.java)
                        .registerC(account, password, password).await()
            }
            register.value = httpResponse

        }



    }

    fun register(account: String, password: String, rPassword:String): Disposable {
        return RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).register(account, password,
                rPassword)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({
                    register.value = it
                }, {
                    register.value = HttpResponse(null, 500, it.message)
                })
    }

    fun logout(): Disposable {

        return RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).logout()
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({
                    logout.value = it
                }, {
                    logout.value = HttpResponse(null, 500, it.message)
                })

    }
}