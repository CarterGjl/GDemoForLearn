package aop.demo.jetpack.android.jetpack.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
    private val _likeNumber: MutableLiveData<Int> = MutableLiveData<Int>().apply {
        value = 0
    }
    val likeNumber = _likeNumber

    fun addLike(count: Int) {

        _likeNumber.value = _likeNumber.value?.plus(count)
    }

    fun unplusLike(count: Int) {
        _likeNumber.value = _likeNumber.value?.minus(count)
    }

}

//class TestViewModel : ViewModel() {
//
//
//    private var mIntegerMutableLiveData: MutableLiveData<Int>? = null
//
//    init {
//        if (mIntegerMutableLiveData == null) {
//            mIntegerMutableLiveData = MutableLiveData()
//        }
//    }
//}

//class TestViewModel : ViewModel() {
//
//
//    var integerMutableLiveData: MutableLiveData<Int>? = null
//        private set
//
//    init {
//        if (integerMutableLiveData == null) {
//            integerMutableLiveData = MutableLiveData()
//        }
//    }
//}

