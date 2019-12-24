package aop.demo.jetpack.android.jetpack.service

import android.media.browse.MediaBrowser
import android.os.Build
import android.os.Bundle
import android.service.media.MediaBrowserService
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MusicService: MediaBrowserService() {
    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowser.MediaItem>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}