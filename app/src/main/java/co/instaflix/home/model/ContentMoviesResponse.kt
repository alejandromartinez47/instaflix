package co.instaflix.home.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class ContentMoviesResponse(
    @SerializedName("results") @Expose val listContent: List<Movies>?
) : Parcelable