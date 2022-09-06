package gu_android_team.modernbooklibrary.data.datasource.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalBook(
    @PrimaryKey
    @ColumnInfo(name = "isbn13")
    val isbn13: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "subtitle")
    val subtitle: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "publisher")
    val publisher: String,
    @ColumnInfo(name = "isbn10")
    val isbn10: String,
    @ColumnInfo(name = "pages")
    val pages: String,
    @ColumnInfo(name = "year")
    val year: String,
    @ColumnInfo(name = "desc")
    val description: String,
    @ColumnInfo(name = "image")
    val image: String
)
