package com.tution.wanandroidkotlin.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel(), LifecycleObserver {

    val mException: MutableLiveData<Throwable> = MutableLiveData()


    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch { block() }
    }

    suspend fun <T> launchOnIo(block: suspend CoroutineScope.() -> Unit) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock,{},{},handleCancellationExceptionManually = false)
        }
    }

    fun launchOnUITryCatch(tryBlock: suspend CoroutineScope.() -> Unit,
                           catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
                           finallyBlock: suspend CoroutineScope.() -> Unit,
                           handleCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    fun launchOnUITryCatch(tryBlock: suspend CoroutineScope.() -> Unit,
                           handleCancellationExceptionManually: Boolean = false
    ) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, handleCancellationExceptionManually)
        }
    }
    private suspend fun tryCatch(
            tryBlock: suspend CoroutineScope.() -> Unit,
            catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
            finallyBlock: suspend CoroutineScope.() -> Unit,
            handleCancellationExceptionManually: Boolean = false) {

        coroutineScope {
            try {
                tryBlock()
            } catch (e: Throwable) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }
}