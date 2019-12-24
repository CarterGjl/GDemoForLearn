package aop.demo.jetpack.android.jetpack.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.annotations.NonNull


class HomeSortItemDecoration(space: Int, private val headSpace: Int, private val footSpace: Int) : RecyclerView.ItemDecoration() {

    private val space: Int = space / 2

    override fun getItemOffsets(@NonNull outRect: Rect, @NonNull view: View, @NonNull parent: RecyclerView, @NonNull state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        when (parent.getChildAdapterPosition(view)) {
            0 -> {
                outRect.left = headSpace
                outRect.right = space
            }
            state.itemCount - 1 -> {
                outRect.left = space
                outRect.right = footSpace
            }
            else -> {
                outRect.left = space
                outRect.right = space
            }
        }
    }

}