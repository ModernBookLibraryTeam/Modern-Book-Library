package gu_android_team.modernbooklibrary.ui.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import gu_android_team.modernbooklibrary.R
import gu_android_team.modernbooklibrary.databinding.ItemMainListsBinding
import gu_android_team.modernbooklibrary.domain.FakeBook
import gu_android_team.modernbooklibrary.utils.MainDiffUtilCallback

class MainRecyclerViewAdapter :
    RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewViewHolder>() {

    private var booksList = emptyList<FakeBook>()

    inner class MainRecyclerViewViewHolder(binding: ItemMainListsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val bookTitle = binding.mainItemTitleTextView
        val bookCover = binding.mainItemImageView
        val bookSubtitle = binding.mainItemSubtitleTextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewViewHolder {
        val itemBinding = ItemMainListsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MainRecyclerViewViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MainRecyclerViewViewHolder, position: Int) {
        with(holder) {
            bookTitle.text = booksList[position].title
            bookSubtitle.text = booksList[position].subtitle
            bookCover.load(booksList[position].coverUrl) {
                placeholder(R.drawable.ic_baseline_image_24)
            }
        }
    }

    override fun getItemCount(): Int = booksList.size

    fun updateData(newBooksList: List<FakeBook>) {
        val diffUtil = MainDiffUtilCallback(booksList, newBooksList)
        val result = DiffUtil.calculateDiff(diffUtil)
        booksList = newBooksList
        result.dispatchUpdatesTo(this@MainRecyclerViewAdapter)
    }
}