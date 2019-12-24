package aop.demo.jetpack.android.jetpack.data

abstract class ShareItem(val type: Int) {

    companion object {
        const val TYPE_LINK = 0
    }

    abstract fun doShare(shareListener: ShareListener)
}

interface ShareListener {
    companion object {
        const val STATE_SUCC: Int = 0
        const val STATE_FAIL: Int = 1
    }

    fun onCallback(state: Int, msg: String);
}

class Link(val link: String, val title: String, content: String) : ShareItem(TYPE_LINK) {
    override fun doShare(shareListener: ShareListener) {
        println("do link share")
    }
}