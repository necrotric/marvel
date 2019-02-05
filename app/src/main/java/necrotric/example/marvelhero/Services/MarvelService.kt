package necrotric.example.marvelhero.Services

import io.reactivex.Single
import necrotric.example.marvelhero.Models.*

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("/v1/public/characters")
    fun getCharacters(@Query("ts") ts: String,
                      @Query("apikey") apikey: String,
                      @Query("hash") hash: String,
                      @Query("nameStartsWith") nameStartsWith: String,
                      @Query("offset") offset: Int,
                      @Query("limit") limit: Int): Single<Api<Array<Hero>>>


    @GET("/v1/public/characters/{id}")
    fun getCharactersById(@Path("id") id: Int,
                          @Query("ts") ts: String,
                          @Query("apikey") apikey: String,
                          @Query("hash") hash: String): Single<Api<Array<Hero>>>


    @GET("/v1/public/series")
    fun getSeries(@Query("ts") ts: String,
                  @Query("apikey") apikey: String,
                  @Query("hash") hash: String,
                  @Query("limit") limit: Int,
                  @Query("offset") offset: Int,
                  @Query("titleStartsWith") titleStartsWith: String): Single<Api<Array<Series>>>
}
