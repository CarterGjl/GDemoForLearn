package com.tution.wanandroidkotlin.util

import android.app.Activity
import com.tution.wanandroidkotlin.R
import com.tution.wanandroidkotlin.bean.SystemChild
import com.tution.wanandroidkotlin.bean.WanResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException

const val TOOL_URL = "http://www.wanandroid.com/tools"
const val GITHUB_PAGE = "https://github.com/lulululbj/wanandroid"
const val ISSUE_URL = "https://github.com/lulululbj/wanandroid/issues"

suspend fun executeResponse(response: WanResponse<Any>, successBlock: suspend CoroutineScope.() -> Unit,
                            errorBlock: suspend CoroutineScope.() -> Unit) {
    coroutineScope {
        if (response.errorCode == -1) errorBlock()
        else successBlock()
    }
}

fun Activity.onNetError(e: Throwable, func: (e: Throwable) -> Unit) {
    if (e is HttpException) {
        toast(R.string.net_error)
        func(e)
    }
}

fun WanResponse<Any>.isSuccess(): Boolean = this.errorCode == 0

fun transFormSystemChild(children: List<SystemChild>): String {
    return children.joinToString("     ", transform = { child -> child.name })
}