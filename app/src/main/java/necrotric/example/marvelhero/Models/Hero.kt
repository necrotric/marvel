package necrotric.example.marvelhero.Models

import android.os.Parcel
import android.os.Parcelable

class Hero constructor(val id: Int, val name: String, val description: String, val thumbnail: Image, val url: String, val series: SeriesList) {

}