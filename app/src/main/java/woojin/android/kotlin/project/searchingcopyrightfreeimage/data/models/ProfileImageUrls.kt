package woojin.android.kotlin.project.searchingcopyrightfreeimage.data.models


import com.google.gson.annotations.SerializedName

data class ProfileImageUrls(
    @SerializedName("large")
    val large: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("small")
    val small: String?
)