package co.instaflix.home.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
open class Series(
    @SerializedName("name") @Expose val name: String?,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("id") @Expose val id: String,
    @SerializedName("vote_average") @Expose val calification: Float


) : Parcelable