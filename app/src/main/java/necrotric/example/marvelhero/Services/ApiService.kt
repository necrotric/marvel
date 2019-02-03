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

    fun chooseReq(reqType: String, search:String=""): Array<*>? {


        val time = getNow()
        val hash = Constant().calcHash(time)
        var apiResult:Array<*>? = null


        when (reqType) {
//            "series" -> {
//                service.getSeries(time, pubKey, hash).subscribeOn(Schedulers.io()).subscribe { wrapper -> ar = wrapper.data.results }
//            }
            "heroesSearch" -> {
                service.getCharacters(time, pubKey, hash,search).subscribeOn(Schedulers.io()).subscribe { wrapper -> apiResult = wrapper.data.results }
            }
        }
        while (apiResult == null || apiResult!!.isEmpty())
            Thread.sleep(5)
        return apiResult
    }


}