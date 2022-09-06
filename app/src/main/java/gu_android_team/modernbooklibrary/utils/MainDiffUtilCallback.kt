package gu_android_team.modernbooklibrary.utils

import androidx.recyclerview.widget.DiffUtil
import gu_android_team.modernbooklibrary.domain.Book

class MainDiffUtilCallback(
    private val oldList: List<Book>,
    private val newList: List<Book>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isbn13 == newList[newItemPosition].isbn13
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition].title == newList[newItemPosition].title &&
                oldList[oldItemPosition].image == newList[newItemPosition].image &&
                oldList[oldItemPosition].subtitle == newList[newItemPosition].subtitle
    }

}