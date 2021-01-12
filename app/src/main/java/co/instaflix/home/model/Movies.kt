package co.instaflix.home.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
open class Movies(
    @SerializedName("title") @Expose val title: String?,
    @SerializedName("poster_path") @Expose val posterPath: String,
    @SerializedName("backdrop_path") @Expose val backdropPath: String,
    @SerializedName("overview") @Expose val overview: String,
    @SerializedName("id") @Expose val id: String,
    @SerializedName("vote_average") @Expose val calification: Double,
    @SerializedName("runtime") @Expose val duration: Int


) : Parcelable