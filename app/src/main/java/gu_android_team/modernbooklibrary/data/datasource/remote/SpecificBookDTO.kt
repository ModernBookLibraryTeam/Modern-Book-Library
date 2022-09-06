package gu_android_team.modernbooklibrary.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class SpecificBookDTO(
    @SerializedName("title") val title: String?,
    @SerializedName("subtitle") val subtitle: String?,
    @SerializedName("authors") val authors: String?,
    @SerializedName("publisher") val publisher: String?,
    @SerializedName("isbn10") val isbn10: String?,
    @SerializedName("pages") val pages: String?,
    @SerializedName("year") val year: String?,
    @SerializedName("desc") val desc: String?,
    @SerializedName("image") val image: String?
)