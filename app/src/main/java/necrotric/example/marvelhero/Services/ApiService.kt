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

    fun heroApiRequest(search:String="A", offset: Int =0): Array<*>? {
        var searchString = search
        if(searchString == ""){
            searchString =randomizeAlphabet()
        }
        val time = getNow()
        val hash = Constant().calcHash(time)
        var apiResult:Array<*>? = null
        println(searchString)

        service.getCharacters(time, pubKey, hash, searchString,offset, 10).subscribeOn(Schedulers.io()).subscribe { wrapper -> apiResult = wrapper.data.results }

            while (apiResult == null) {
                //If to much data is loaded app crash anyway.bat
               Thread.sleep(50)
//                println("IM CRASHING")
            }

        return apiResult
    }

    fun serieApiRequest(search : String ="a", offset: Int): Array<*>?{
        if(search == ""){

        }
        val time = getNow()
        val hash = Constant().calcHash(time)
        var apiResult: Array<*>? = null

        service.getSeries(time, pubKey, hash,10,offset, search).subscribeOn(Schedulers.io()).subscribe { wrapper -> apiResult = wrapper.data.results }
        while( apiResult == null){
            Thread.sleep(50)
            println("Im crashing")
        }

        return apiResult
    }


    fun randomizeAlphabet():String{
         var searchString: String = ""
        val rnds = (1..26).random()
        when(rnds) {
            1 -> searchString ="A"
            2 -> searchString ="B"
            3 -> searchString = "C"
            4 -> searchString = "D"
            5 -> searchString = "E"
            6 -> searchString = "F"
            7 -> searchString =  "G"
            8 -> searchString =  "H"
            9 -> searchString =   "I"
            10 -> searchString =  "J"
            11-> searchString =   "K"
            12-> searchString =   "L"
            13-> searchString =   "M"
            14-> searchString =   "N"
            15 -> searchString =   "O"
            16 -> searchString =   "P"
            17 -> searchString =    "Q"
            18 -> searchString =   "R"
            19 -> searchString =   "S"
            20 -> searchString =    "T"
            21 -> searchString =    "U"
            22 -> searchString =    "V"
            23 -> searchString =    "W"
            24 -> searchString =    "X"
            25 -> searchString =    "Y"
            26 -> searchString =    "Z"
            28 -> searchString = "0"
            29 -> searchString = "1"
            30 -> searchString = "2"
            31 -> searchString = "3"
            32 -> searchString = "4"
            33 -> searchString = "5"
            34 -> searchString = "6"
            35 -> searchString = "7"
            36 -> searchString = "8"
            37 -> searchString = "9"

        }
        return searchString
    }
}