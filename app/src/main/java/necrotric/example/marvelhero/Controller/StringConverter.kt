package necrotric.example.marvelhero.Controller

import kotlinx.android.synthetic.main.activity_main.*

object StringConverter {
    fun getValIfNull(searchField: String): String? {
        var value: String? = searchField
        if (value.toString() == "") {
            value = null
        }
        return value
    }
}