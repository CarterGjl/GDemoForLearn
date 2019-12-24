package aop.demo.jetpack.android.jetpack.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import aop.demo.jetpack.android.jetpack.R
import aop.demo.jetpack.android.jetpack.http.RetrofitClient
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        return root
    }
    private val mainScope = MainScope()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBtnChange.setOnClickListener {

//            GlobalScope.launch(Dispatchers.Main) {
//                val jsonObject = fetchData()
//                text_home.text = jsonObject.toString()
//            }
            mainScope.launch(Dispatchers.Main) {
                val await = RetrofitClient.reqApi.getDatasAsync().await()
//                val jsonObject = fetchData()
                text_home.text = await.toString()
            }

//            val trim = mEtInput.text.trim()
//            homeViewModel.changeText("change text $trim")
        }
    }

    private suspend fun fetchData(): JsonObject {
        return withContext(Dispatchers.IO) {
            val await = RetrofitClient.reqApi.getDatasAsync().await()
            await
        }
    }
}