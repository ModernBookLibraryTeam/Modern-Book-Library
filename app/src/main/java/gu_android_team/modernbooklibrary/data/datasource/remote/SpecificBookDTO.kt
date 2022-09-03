package gu_android_team.modernbooklibrary.data.datasource.remote

import com.google.gson.annotations.SerializedName

data class SpecificBookDTO(
    @SerializedName("title") val title: String?,
    @SerializedName("authors") val authors: String?,
    @SerializedName("pages") val pages: String?,
    @SerializedName("year") val year: String?,
    @SerializedName("desc") val desc: String?,
    @SerializedName("image") val image: String?
)