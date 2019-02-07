package necrotric.example.marvelhero.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_series_search.*
import necrotric.example.marvelhero.Adapter.SeriesRecycleAdapter
import necrotric.example.marvelhero.Models.Series
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class SeriesSearchActivity : AppCompatActivity() {
    lateinit var adapter: SeriesRecycleAdapter
    var seriesNewList = ArrayList<Series>()
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_search)
        seriesSearchBtn.setOnClickListener {
            count = 0
            seriesNewList = serieSearchFunction(count)
            if (seriesNewList.size > 1) {
                adapter = SeriesRecycleAdapter(this, seriesNewList) { heroitem ->
                    //TODO return id to showSeriesInfo
                }
                seriesListView.adapter = adapter
                val layoutManager = LinearLayoutManager(this)
                seriesListView.layoutManager = layoutManager
                seriesListView.setHasFixedSize(true)
            }
        }
        loadMore.setOnClickListener {
            count = count + 5
            seriesNewList = serieSearchFunction(count)
            if (seriesNewList.size > 1) {
                adapter = SeriesRecycleAdapter(this, seriesNewList) { heroitem ->
                    //TODO return id to showSeriesInfo
                }
                seriesListView.adapter = adapter
                val layoutManager = LinearLayoutManager(this)
                seriesListView.layoutManager = layoutManager
                seriesListView.setHasFixedSize(true)
            } else {
                count -= 5
            }
        }

        loadPrevious.setOnClickListener {
            if (count >= 5) {
                count -= 5
            }
            seriesNewList = serieSearchFunction(count)
            if (seriesNewList.size > 1) {
                adapter = SeriesRecycleAdapter(this, seriesNewList) { heroitem ->
                    //TODO return id to showSeriesInfo
                }
                seriesListView.adapter = adapter
                val layoutManager = LinearLayoutManager(this)
                seriesListView.layoutManager = layoutManager
                seriesListView.setHasFixedSize(true)
            } else {
                count += 5
            }


        }
    }

    fun serieSearchFunction(count: Int): ArrayList<Series> {
        var returnSeries = ArrayList<Series>()
        returnSeries = ArrayList()
//        val seriesResult = ApiService.serieApiRequest(seriesSearchField.text.toString(), count)

//        if (seriesResult != null) {
//            for (serie in seriesResult) {
//
//                serie as Series
//                returnSeries.add(serie)
//
//                println("Title: " + serie.title)
//                println("Description: " + serie.description)
//                println("Rating: " + serie.rating)
//                println(serie.thumbnail.path)
//
//                for (i in serie.characters.items) {
//                    println("Heroes " + i.name)
//                }
//            }
//        }
        return returnSeries
    }
}
