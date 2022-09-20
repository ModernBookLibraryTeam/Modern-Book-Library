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

class SearchRecyclerViewAdapter(private val onAdapterBookListener: OnBookListener) :
    RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchRecyclerViewViewHolder>() {
    val searchedBooks = mutableListOf<Book>()

    fun setSearchedBooksList(searchedListParam: List<Book>) {
        val diffCallBack = MainDiffUtilCallback(this.searchedBooks, searchedListParam)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        this.searchedBooks.clear()
        this.searchedBooks.addAll(searchedListParam)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setSearchedBooksNewPage(searchedListParam: List<Book>) {
        val diffCallBack = MainDiffUtilCallback(this.searchedBooks, searchedListParam)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        this.searchedBooks.addAll(searchedBooks.size,searchedListParam)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchRecyclerViewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_list, parent, false)
        return SearchRecyclerViewViewHolder(view, onAdapterBookListener)
    }

    override fun onBindViewHolder(holder: SearchRecyclerViewViewHolder, position: Int) {
        holder.binding(searchedBooks[position])
    }

    override fun getItemCount() = searchedBooks.size

    inner class SearchRecyclerViewViewHolder(view: View, var onBookListener: OnBookListener) :
        RecyclerView.ViewHolder(view),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        private val image = view.findViewById<ShapeableImageView>(R.id.searchItemImageView)
        private val title = view.findViewById<MaterialTextView>(R.id.searchItemTitleTextView)
        private val author = view.findViewById<MaterialTextView>(R.id.searchItemAuthorTextView)

        fun binding(item: Book) {
            image.load(item.image) {
                placeholder(R.drawable.ic_baseline_image_24)
            }
            title.text = item.title
            author.text = item.authors
        }

        override fun onClick(p0: View?) {
            onBookListener.onBookClick(adapterPosition)
        }
    }

    interface OnBookListener {
        fun onBookClick(position: Int)
    }
}
