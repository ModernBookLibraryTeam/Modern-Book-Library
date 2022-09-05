package gu_android_team.modernbooklibrary.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalBook(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "created_date")
    val createdDate: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "year")
    val publishDate: String,
    @ColumnInfo(name = "book_jacket")
    val bookJacket: String
)
