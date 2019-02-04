package necrotric.example.marvelhero.Services

import io.reactivex.Single
import necrotric.example.marvelhero.Models.*

import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("characters")
    fun getCharacters(@Query("ts") ts: String,
                      @Query("apikey") apikey: String,
                      @Query("hash") hash: String,
                      @Query("nameStartsWith") nameStartsWith: String,
                      @Query("limit") limit: Int): Single<Api<Array<Hero>>>
}