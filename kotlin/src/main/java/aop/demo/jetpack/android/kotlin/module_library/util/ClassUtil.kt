package aop.demo.jetpack.android.kotlin.module_library.util

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import aop.demo.jetpack.android.kotlin.module_library.NoViewModel
import java.lang.reflect.ParameterizedType

object ClassUtil {

    fun <T> getViewModel(obj: Any): Class<T>? {
        val currentClass = obj.javaClass
        var genericClass = getGenericClass<T>(currentClass, ViewModel::class.java)

        return if (genericClass == null || genericClass == AndroidViewModel::class
                        .java || genericClass == NoViewModel::class.java) {
            null
        } else {
            genericClass
        }
    }

    private fun <T> getGenericClass(klass: Class<*>, filterClass: Class<*>): Class<T>? {
        val type = klass.genericSuperclass
        if (type == null || type !is ParameterizedType) return null
        val types = type.actualTypeArguments
        for (t in types) {
            val tClass = t as Class<T>
            if (filterClass.isAssignableFrom(tClass)) {
                return tClass
            }
        }
        return null
    }
}