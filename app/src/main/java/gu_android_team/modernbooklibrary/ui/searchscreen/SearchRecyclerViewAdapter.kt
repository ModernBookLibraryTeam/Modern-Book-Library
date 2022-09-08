package gu_android_team.modernbooklibrary.ui.searchscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.domain.Book
import gu_android_team.modernbooklibrary.utils.MainDiffUtilCallback

class SearchRecyclerViewAdapter :
    RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchRecyclerViewViewHolder>() {
    var searchedBooks = listOf<Book>()
        set(listParam) {
            val diffCallBack = MainDiffUtilCallback(this.searchedBooks, listParam)
            DiffUtil.calculateDiff(diffCallBack).also {
                it.dispatchUpdatesTo(this)
            }
            field = listParam
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchRecyclerViewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_list, parent, false)
        return SearchRecyclerViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchRecyclerViewViewHolder, position: Int) {
        holder.binding(searchedBooks[position])
    }

    override fun getItemCount() = searchedBooks.size

    inner class SearchRecyclerViewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.findViewById<ShapeableImageView>(R.id.searchItemImageView)
        val title = view.findViewById<MaterialTextView>(R.id.searchItemTitleTextView)
        val author = view.findViewById<MaterialTextView>(R.id.searchItemAuthorTextView)
        fun binding(item: Book) {
            image.load(item.image) {
                placeholder(R.drawable.ic_baseline_image_24)
            }
            title.text = item.title
            author.text = item.authors
        }
    }
}
