//package aop.demo.jetpack.android.jetpack.adapter
//
//
//import android.view.View
//import android.view.ViewGroup
//import androidx.paging.PagedListAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//
//class APageAdapter : PagedListAdapter<Any, APageAdapter.ViewHolder>(object : DiffUtil.ItemCallback<Any>() {
//    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
//        return false
//    }
//
//    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
//        return false
//    }
//}) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder(parent)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
//}
