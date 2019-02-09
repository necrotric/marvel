package necrotric.example.marvelhero.Models

class Series constructor(val id: Int, val title: String, val description: String, val resourceURI: String, val urls: Array<Urls>,
                         val startYear: Int, val endYear: Int, val rating: String, val  thumbnail: Image,
                         val creators: SeriesList, val characters: SeriesList, val comics: SeriesList) {
//    override fun toString(): String {
//        return title
//    }
}