package aop.demo.jetpack.android.jetpack

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import aop.demo.jetpack.android.jetpack.bean.AddUrlRequestBody
import aop.demo.jetpack.android.jetpack.http.RetrofitClient
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        launch {
            val await = RetrofitClient.reqApi.getHelloAsync().await()

            println("request $await")
            val addUrlRequestBody = AddUrlRequestBody("test_id")
            val await1 = RetrofitClient.reqApi.addIDAsync(addUrlRequestBody).await()
            println("createRoom Request $await1")
        }
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val async = async {

            val name = "打发大水发"
            name
        }
        val mainScope = MainScope()
        mainScope.launch {
            val async1 = async {
                return@async ""
            }
        }

    }
    suspend fun getImage(){
        var withContext = withContext(Dispatchers.IO) {
            println("get success ")
            ""
        }

    }
}
