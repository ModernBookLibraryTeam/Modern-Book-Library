package gu_android_team.modernbooklibrary.ui.favoritesscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.ItemFavoritesListBinding
import gu_android_team.modernbooklibrary.domain.Book

class FavoritesScreenAdapter(
    private val onItemClicked: (Book) -> Unit,
    private val onItemClickedDelete: (Book) -> Unit
) : ListAdapter<Book, FavoritesScreenAdapter.FavoritesViewHolder>(diffCallback) {

    inner class FavoritesViewHolder(private var binding: ItemFavoritesListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            with(binding) {
                favoritesItemImageView.load(book.image) {
                    placeholder(R.drawable.ic_baseline_image_24)
                }
                favoritesItemTitleTextView.text = book.title
                favoritesItemAuthorTextView.text = book.authors
                favoritesItemSubtitleTextView.text = book.subtitle

                favoritesItemRemoveImageView.setOnClickListener {
                    onItemClickedDelete(book)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val viewHolder = FavoritesViewHolder(
            ItemFavoritesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.bindingAdapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.isbn13 == newItem.isbn13
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

        }
    }
}