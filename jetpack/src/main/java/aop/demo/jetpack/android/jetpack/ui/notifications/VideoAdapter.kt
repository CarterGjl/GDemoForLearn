package aop.demo.jetpack.android.jetpack.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import aop.demo.jetpack.android.jetpack.R
import kotlinx.android.synthetic.main.video_item.view.*

class VideoAdapter(private val list: MutableList<NotificationsFragment.Video>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoHolder(LayoutInflater.from(parent.context).inflate(R.layout.video_item,
                parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val videoHolder = holder as VideoHolder
        videoHolder.bindData(list[position])
    }

    class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(video: NotificationsFragment.Video) {
            itemView.mTvDuration.text = "${video.duration}"
            itemView.mTvTitle.text = video.name
        }
    }
}