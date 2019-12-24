package aop.demo.jetpack.android.animationdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var list = arrayListOf<String>()
        list.stream().allMatch()
        initEvent()
    }

    private fun initEvent() {
        mMove.setOnClickListener {
            mIv.animate().translationX(100F)
        }
    }
}
