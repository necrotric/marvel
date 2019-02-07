package necrotric.example.marvelhero.Models

class Urls constructor(val type: String, val url: String) {
    override fun toString(): String {
        return url
    }
}