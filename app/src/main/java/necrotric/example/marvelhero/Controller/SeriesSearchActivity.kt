package necrotric.example.marvelhero.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_series_search.*
import necrotric.example.marvelhero.Models.Series
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class SeriesSearchActivity : AppCompatActivity() {

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_search)

        var seriesSearch = seriesSearchField.text
        seriesSearchBtn.setOnClickListener {
            count = 0
            serieSearchFunction(seriesSearch.toString(),count)

        }
        loadMore.setOnClickListener {
            count = count + 10
            serieSearchFunction(seriesSearch.toString(),count)
        }
        loadPrevious.setOnClickListener {
            count = count - 10
            serieSearchFunction(seriesSearch.toString(),count)
        }
    }

    fun serieSearchFunction(search: String, count: Int){
        val seriesResult = ApiService.serieApiRequest(search.toString(), count)

        if (seriesResult != null) {
            for (serie in seriesResult){

                serie as Series
                println("Title: " + serie.title)
                println("Description: " +serie.description)
                println("Rating: " + serie.rating)
            }


        }
    }
}
