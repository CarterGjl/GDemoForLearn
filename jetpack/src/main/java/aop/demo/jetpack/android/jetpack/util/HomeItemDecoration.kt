//package aop.demo.jetpack.android.jetpack.util
//
//import aop.demo.jetpack.android.jetpack.bean.HomeListInfo
//import sun.util.locale.provider.LocaleProviderAdapter.getAdapter
//import android.R.attr.right
//import android.R.attr.left
//import android.graphics.Rect
//import android.view.View
//import androidx.recyclerview.widget.GridLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.StaggeredGridLayoutManager
//import com.stx.xhb.androidx.XBannerUtils.dp2px
//import io.reactivex.annotations.NonNull
//
//
//class HomeItemDecoration(space: Int, private val headSpace: Int, private val footSpace: Int) : RecyclerView.ItemDecoration() {
//
//    private val space: Int
//    private val topAndBottom = DisplayUtils.dp2px(15f)
//
//    init {
//        this.space = space / 2
//    }
//
//    override fun getItemOffsets(@NonNull outRect: Rect, @NonNull view: View, @NonNull parent: RecyclerView, @NonNull state: RecyclerView.State) {
//        super.getItemOffsets(outRect, view, parent, state)
//        val layoutManager = parent.getLayoutManager() as GridLayoutManager
//        val itemPosition = parent.getChildAdapterPosition(view)
//        if (layoutManager != null) {
//            val spanSize = layoutManager!!.getSpanSizeLookup().getSpanSize(itemPosition)
//            //一列的时候
//            if (spanSize == layoutManager!!.getSpanCount()) {
//                if (layoutManager!!.getItemViewType(view) !== HomeListInfo.HEARD_TYPE &&
//                        layoutManager!!.getItemViewType(view) !== HomeListInfo.LIST_SQUARE_TYPE &&
//                        layoutManager!!.getItemViewType(view) !== HomeListInfo.LIST_RECTANGLE_TYPE) {
//                    outRect.left = headSpace
//                    outRect.right = footSpace
//                }
//            } else if (spanSize == 2) {
//                val infoListIndex = getListPosition(parent, view)
//                //如果是在左边
//                if (isFirstColumn(3, infoListIndex)) {
//                    outRect.left = headSpace
//                    outRect.right = space
//                    //如果是在右边
//                } else if (isLastColumn(parent, infoListIndex, 3)) {
//                    outRect.left = space
//                    outRect.right = footSpace
//                } else {
//                    //在中间
//                    outRect.left = space
//                    outRect.right = space
//                }
//            } else if (spanSize == 3) {
//                val infoListIndex = getListPosition(parent, view)
//                //如果在左边
//                if (isFirstColumn(2, infoListIndex)) {
//                    outRect.left = headSpace
//                    outRect.right = space
//                } else {
//                    //右边
//                    outRect.left = space
//                    outRect.right = footSpace
//                }
//            }//二列的时候怎么
//            //三列的时候
//        }
//
//        if (itemPosition == 0) {
//            outRect.top = topAndBottom
//        }
//
//        outRect.bottom = topAndBottom
//    }
//
//
//    /**
//     * 获取view对于的坐标
//     *
//     * @param parent
//     * @param view
//     * @return
//     */
//    private fun getListPosition(parent: RecyclerView, view: View): Int {
//        val childLayoutPosition = parent.getChildLayoutPosition(view)
//        val homeAdapter = parent.adapter as HomeAdapter
//        val homeListInfo = homeAdapter.getData().get(childLayoutPosition)
//        return homeListInfo.listIndex
//    }
//
//
//    /**
//     * 是否是第一列
//     *
//     * @param spanCount
//     * @param readPosition
//     * @return
//     */
//    private fun isFirstColumn(spanCount: Int, readPosition: Int): Boolean {
//        return readPosition % spanCount == 0
//    }
//
//    /**
//     * 判断是否是最后一列
//     *
//     * @param parent
//     * @param pos
//     * @param spanCount
//     * @return
//     */
//    private fun isLastColumn(parent: RecyclerView, pos: Int, spanCount: Int): Boolean {
//        val layoutManager = parent.getLayoutManager()
//        if (layoutManager is GridLayoutManager) {
//            // 如果是最后一列，则不需要绘制右边
//            return (pos + 1) % spanCount == 0
//        } else if (layoutManager is StaggeredGridLayoutManager) {
//            val orientation = layoutManager
//                    .orientation
//            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
//                // 最后一列
//                return (pos + 1) % spanCount == 0
//            }
//        }
//        return false
//    }
//
//
//}
