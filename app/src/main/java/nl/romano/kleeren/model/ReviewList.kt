package nl.romano.kleeren.model

import com.google.gson.annotations.SerializedName

data class ReviewList(
    @SerializedName("Review")
    val reviews: List<Review>
)
