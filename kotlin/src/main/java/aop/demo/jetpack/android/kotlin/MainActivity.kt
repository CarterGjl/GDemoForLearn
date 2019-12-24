package aop.demo.jetpack.android.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.net.toUri
import aop.demo.jetpack.android.kotlin.impl.repository.model.LoginBean
import aop.demo.jetpack.android.kotlin.impl.ui.ui.login.LoginActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_scene1)
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivity(intent)

    }


    suspend fun getUserCoroutine() = suspendCoroutine<LoginBean> {
        continuation ->
        continuation.resume(LoginBean(false, listOf(), listOf(),"7777","dafa",121341,"fadsfas",
                "fdasfs","fdsafdasf",1,"fdasfdas"))
        continuation.resumeWithException(exception = Exception("something wrong"))
    }
}
