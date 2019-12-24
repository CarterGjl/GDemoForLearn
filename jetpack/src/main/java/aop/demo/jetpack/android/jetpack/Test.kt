package aop.demo.jetpack.android.jetpack

import java.util.*

fun initBookList() = listOf(
        Book("Kotlin", "小明", 55, Group.Technology),
        Book("中国民俗", "小黄", 25, Group.Humanities),
        Book("娱乐杂志", "小红", 19, Group.Magazine),
        Book("灌篮", "小张", 20, Group.Magazine),
        Book("资本论", "马克思", 50, Group.Political),
        Book("Java", "小张", 30, Group.Technology),
        Book("Scala", "小明", 75, Group.Technology),
        Book("月亮与六便士", "毛姆", 25, Group.Fiction),
        Book("追风筝的人", "卡勒德", 30, Group.Fiction),
        Book("文明的冲突与世界秩序的重建", "塞缪尔·亨廷顿", 24, Group.Political),
        Book("人类简史", "尤瓦尔•赫拉利", 40, Group.Humanities)
)

data class Book(
        val name: String,
        val author: String,
        //单位元，假设只能标价整数
        val price: Int,
        //group为可空变量，假设可能会存在没有（不确定）分类的图书
        val group: Group?)


enum class Group{
    //科技
    Technology,
    //人文
    Humanities,
    //杂志
    Magazine,
    //政治
    Political,
    //小说
    Fiction
}



class Test {

    fun test(): Unit {
        println("test 哈哈哈哈")
    }
}
class Created{
    fun charge(price: Int){
        println("pay $price yuan")
    }
}
class  BookStoreOption{
    private  val bookCollection = initBookList()


}