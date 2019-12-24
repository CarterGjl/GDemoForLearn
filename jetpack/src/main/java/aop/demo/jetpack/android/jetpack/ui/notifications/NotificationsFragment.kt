package aop.demo.jetpack.android.jetpack.ui.notifications

import android.content.ContentUris
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import aop.demo.jetpack.android.jetpack.R
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.util.concurrent.TimeUnit

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        notificationsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoList = mutableListOf<Video>()

//        val projection = arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.DURATION,
//                MediaStore.Video.Media.SIZE)
//        val selection = "${MediaStore.Video.Media.DURATION} >= ?"
//        val selectionArgs = arrayOf(TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES).toString())
//        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"
//        val query = requireActivity().contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
//                selection, selectionArgs, sortOrder)
//        query?.use { cursor ->
//            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
//            val nameColumn =
//                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
//            val durationColumn =
//                    cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
//            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
//
//            while (cursor.moveToNext()) {
//                // Get values of columns for a given video.
//                val id = cursor.getLong(idColumn)
//                val name = cursor.getString(nameColumn)
//                val duration = cursor.getInt(durationColumn)
//                val size = cursor.getInt(sizeColumn)
//
//                val contentUri: Uri = ContentUris.withAppendedId(
//                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                        id
//                )
//
//                // Stores column values and the contentUri in a local object
//                // that represents the media file.
//                videoList += Video(contentUri, name, duration, size)
//            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            query()
        }
        mBtnStart.setOnClickListener {
            println(videoList)

        }
        mBtnStop.setOnClickListener {

        }
        mRvVideo.layoutManager = LinearLayoutManager(requireContext())
        mRvVideo.adapter = VideoAdapter(list = videoList)
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    fun query(): Unit {
        val videoList = mutableListOf<Video>()

        val projection = arrayOf(MediaStore.Downloads._ID, MediaStore.Downloads.DISPLAY_NAME,
                MediaStore.Downloads.DURATION,
                MediaStore.Downloads.SIZE)
        val selection = "${MediaStore.Downloads.SIZE} >= ?"
//        val selectionArgs = arrayOf(TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES).toString())
        val selectionArgs = arrayOf(0.toString())
        val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"
        val query = requireActivity().contentResolver.query(MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                projection,
                selection, selectionArgs, sortOrder)
        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Downloads._ID)
            val nameColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Downloads.DISPLAY_NAME)
            val durationColumn =
                    cursor.getColumnIndexOrThrow(MediaStore.Downloads.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Downloads.SIZE)

            while (cursor.moveToNext()) {
                // Get values of columns for a given video.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val size = cursor.getInt(sizeColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                )

                // Stores column values and the contentUri in a local object
                // that represents the media file.
                videoList += Video(contentUri, name, duration, size)
            }
        }
    }
    data class Video(val uri: Uri, val name: String, val duration: Int, val size: Int)

}


