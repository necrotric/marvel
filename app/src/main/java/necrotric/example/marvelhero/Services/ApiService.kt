package necrotric.example.marvelhero.Services

import io.reactivex.schedulers.Schedulers
import necrotric.example.marvelhero.Constants.Constant


import necrotric.example.marvelhero.Services.MarvelService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val marvelUrl = "https://gateway.marvel.com/v1/public/"
    private val pubKey = Constant().publicKey
    private val service: MarvelService = Retrofit.Builder()
            .baseUrl(marvelUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient())
            .build()
            .create(MarvelService::class.java)


    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.build()
        return okHttpClient
    }
    private fun getNow(): String {
        return System.currentTimeMillis().toString()
    }
    fun oneHero(id: Int): Array<*>?{
        val time = getNow()
        val hash = Constant().calcHash(time)
        var apiResult:Array<*>? = null

        service.getCharactersById(id,time, pubKey,hash ).subscribeOn(Schedulers.io()).subscribe { wrapper -> apiResult = wrapper.data.results }

        while (apiResult == null) {
            //If to much data is loaded app crash anyway.bat
            Thread.sleep(100)
            println("IM CRASHING")
        }
        return apiResult

    }

    fun heroApiRequest(search:String="A"): Array<*>? {
        val time = getNow()
        val hash = Constant().calcHash(time)
        var apiResult:Array<*>? = null

        service.getCharacters(time, pubKey, hash, search,10).subscribeOn(Schedulers.io()).subscribe { wrapper -> apiResult = wrapper.data.results }


            while (apiResult == null) {
                //If to much data is loaded app crash anyway.bat
                Thread.sleep(100)
                println("IM CRASHING")
            }
        return apiResult
    }
}