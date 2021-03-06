package necrotric.example.marvelhero.Services

import io.reactivex.Single
import necrotric.example.marvelhero.Models.*

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelService {

    @GET("/v1/public/characters")
    fun getCharacters(@Query("nameStartsWith") nameStartsWith: String?,
                      @Query("offset") offset: Int,
                      @Query("limit") limit: Int): Single<Api<Array<Hero>>>


    @GET("/v1/public/characters/{id}")
    fun getCharactersById(@Path("id") id: Int): Single<Api<Array<Hero>>>


    @GET("/v1/public/series")
    fun getSeries(@Query("limit") limit: Int,
                  @Query("offset") offset: Int,
                  @Query("titleStartsWith") titleStartsWith: String?): Single<Api<Array<Series>>>

    @GET ("/v1/public/series/{seriesId}")
    fun getSerieById(@Path("seriesId")id: Int): Single<Api<Array<Series>>>
}
