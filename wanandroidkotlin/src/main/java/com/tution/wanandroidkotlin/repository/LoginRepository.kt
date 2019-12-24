package com.tution.wanandroidkotlin.repository

import com.google.gson.Gson
import com.tution.wanandroidkotlin.App
import com.tution.wanandroidkotlin.api.WanService
import com.tution.wanandroidkotlin.bean.User
import com.tution.wanandroidkotlin.http.RetrofitClient
import com.tution.wanandroidkotlin.util.Preference
import java.io.IOException
import com.tution.wanandroidkotlin.bean.Result
class LoginRepository :BaseRepository() {

    private var isLogin by Preference(Preference.IS_LOGIN,false)
    private var userJson by Preference(Preference.USER_GSON, "")
    suspend fun login(userName:String,pwd:String): Result<User>{
        return safeApiCall(call ={
            requestLogin(userName,pwd) },errorMessage = "登录失败!")

    }

    private suspend fun requestLogin(userName: String, passWord: String): Result<User> {
        val response = RetrofitClient.reqApi.login(userName, passWord)
        return if (response.errorCode != -1) {
            val user = response.data
            isLogin = true
            userJson = Gson().toJson(user)
            App.CURRENT_USER = user
            Result.Success(user)
        } else {
            Result.Error(IOException(response.errorMsg))
        }
    }

    suspend fun register(userName: String,passWord: String):Result<User>{
        return safeApiCall(call = { requestRegister(userName,passWord) },errorMessage = "注册失败")
    }

    private suspend fun requestRegister(userName: String,passWord: String):Result<User>{
        val register = RetrofitClient.reqApi.register(userName, passWord,passWord)
        return if (register.errorCode!=-1){
            requestLogin(userName,passWord)
        }else{
            Result.Error(IOException(register.errorMsg))
        }
    }
}