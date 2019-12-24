package aop.demo.jetpack.android.kotlin.module_library

fun main() {
    var name:String? = null

    var age:Int? = null
    if (age == null) {
        age = 1
    }
    name?.let {

    }
    if (name == null) {
        name = "hahhh"
    }
    val user = User(name = name, age = age)

    println("$user name length:"+ name.length.toString())
}

data class User(val age:Int?,val name: String?)