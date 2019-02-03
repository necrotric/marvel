package necrotric.example.marvelhero.Models

import com.google.gson.annotations.SerializedName

class Api<T>(@SerializedName("code") val code: Int,
             @SerializedName("status") val status: String,
             @SerializedName("data") val data: Data<T>,
             @SerializedName("etag") val etag: String)
