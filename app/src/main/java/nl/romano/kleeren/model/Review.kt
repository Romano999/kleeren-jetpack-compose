package nl.romano.kleeren.model

import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("Would recommend")
    val Would_recommend: String,

    @SerializedName("Would not recommend")
    val Would_not_recommend: String,

    @SerializedName("No comment")
    val No_comment: String
)
