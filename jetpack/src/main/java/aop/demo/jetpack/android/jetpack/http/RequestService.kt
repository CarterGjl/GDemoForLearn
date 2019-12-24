package aop.demo.jetpack.android.jetpack.http

import aop.demo.jetpack.android.jetpack.bean.AddUrlRequestBody
import aop.demo.jetpack.android.jetpack.data.Result
import com.google.gson.JsonObject
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface RequestService {


    @GET("wxarticle/chapters/json")
    fun getDatasAsync(): Deferred<JsonObject>

    @GET("hello")
    fun getHelloAsync(): Deferred<Result<String>>

    @POST("createroom")
    fun addIDAsync(@Body x: AddUrlRequestBody): Deferred<Result<String>>
}