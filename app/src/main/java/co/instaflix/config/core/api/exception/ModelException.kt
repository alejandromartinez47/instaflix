package co.instaflix.config.core.api.exception

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelException(
        @SerializedName("error") @Expose var error: String = "",
        @SerializedName("mssg") @Expose var mssg: String = ""
): Parcelable