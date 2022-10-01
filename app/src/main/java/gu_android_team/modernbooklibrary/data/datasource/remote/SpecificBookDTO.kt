package gu_android_team.modernbooklibrary.data.datasource.remote

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class SpecificBookDTO(
    @SerializedName("title") val title: String?,
    @SerializedName("subtitle") val subtitle: String?,
    @SerializedName("authors") val authors: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("isbn10") val isbn10: String?,
    @SerializedName("isbn13") val isbn13: String?,
    @SerializedName("pages") val pages: String?,
    @SerializedName("rating") val rating: String?,
    @SerializedName("year") val year: String?,
    @SerializedName("desc") val desc: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("pdf") val pdfLinksList: JsonObject?
)