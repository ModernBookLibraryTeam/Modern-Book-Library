package gu_android_team.modernbooklibrary.utils

import androidx.recyclerview.widget.DiffUtil
import gu_android_team.modernbooklibrary.domain.FakeBook

class MainDiffUtilCallback(
    private val oldList: List<FakeBook>,
    private val newList: List<FakeBook>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isbn13 == newList[newItemPosition].isbn13
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return oldList[oldItemPosition].title == newList[newItemPosition].title &&
                oldList[oldItemPosition].coverUrl == newList[newItemPosition].coverUrl &&
                oldList[oldItemPosition].subtitle == newList[newItemPosition].subtitle
    }

}