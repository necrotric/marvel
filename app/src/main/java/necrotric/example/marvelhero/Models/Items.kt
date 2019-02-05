package necrotric.example.marvelhero.Models

import android.os.Parcel
import android.os.Parcelable

class Items constructor(val resourceURI: String, val name: String){
    override fun toString(): String {
        return name
    }


}
