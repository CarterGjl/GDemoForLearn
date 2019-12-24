package aop.demo.jetpack.android.databingding.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle

class ViewModelSharePreferences(private val handle: SavedStateHandle, application: Application)
    : AndroidViewModel(application) {

    val number: MutableLiveData<Int>
        get() = handle.getLiveData("num")


    init {
        if (!handle.contains("num")) {
            load()
        }
    }

    private fun load(): Int {
        val int = getApplication<Application>().getSharedPreferences("data", Context.MODE_PRIVATE)
                .getInt("num", 0)
        handle.set("num", int)
        return int
    }

    fun save() {
        var commit = getApplication<Application>().getSharedPreferences("data", Context.MODE_PRIVATE)
                .edit().putInt("num", number.value!!).commit()
    }

    fun add(x: Int): Unit {
        number.value?.let {
            handle.set("num", x + it)
        }

    }
}