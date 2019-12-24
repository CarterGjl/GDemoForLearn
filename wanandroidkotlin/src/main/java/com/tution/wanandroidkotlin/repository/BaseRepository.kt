package com.tution.wanandroidkotlin.repository

import com.tution.wanandroidkotlin.bean.WanResponse
import java.io.IOException

open class BaseRepository {

    suspend fun <T:Any> apiCall(call:suspend()-> WanResponse<T>):WanResponse<T>{
        return call.invoke()
    }
    suspend fun <T : Any> safeApiCall(call: suspend () ->  com.tution.wanandroidkotlin.bean.Result<T>, errorMessage: String): com.tution.wanandroidkotlin.bean.Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            com.tution.wanandroidkotlin.bean.Result.Error(IOException(errorMessage, e))
        }
    }

}