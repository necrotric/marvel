package necrotric.example.marvelhero.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_series_search.*
import necrotric.example.marvelhero.Adapter.SeriesRecycleAdapter
import necrotric.example.marvelhero.Models.Series
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class SeriesSearchActivity : AppCompatActivity() {
    lateinit var adapter: SeriesRecycleAdapter
    var seriesNewList = ArrayList<Series>()
    var searchVal: String? = null
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_search)

        adapter = SeriesRecycleAdapter(this, seriesNewList) { heroitem ->
            //TODO return id to showSeriesInfo
        }


        seriesSearchBtn.setOnClickListener {
            count = 0
            searchVal = getValIfNull()
            ApiService.service.getSeries(10, count, searchVal)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { wrapper ->
                    seriesNewList = ArrayList()
                    for (i in wrapper.data.results) {
                        seriesNewList.add(i)
                    }
                    adapter.series = seriesNewList
                    adapter.notifyDataSetChanged()
                    seriesListView.adapter = adapter
                    val layoutManager = LinearLayoutManager(this)
                    seriesListView.layoutManager = layoutManager
                    seriesListView.setHasFixedSize(true)
                }

        }
        loadMore.setOnClickListener {
            count += 10
            searchVal = getValIfNull()
            ApiService.service.getSeries(10, count, searchVal)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { wrapper ->
                    seriesNewList = ArrayList()
                    for (i in wrapper.data.results) {
                        seriesNewList.add(i)
                    }
                    if (!seriesNewList.isEmpty()) {
                        adapter.series = seriesNewList
                        adapter.notifyDataSetChanged()
                        seriesListView.adapter = adapter
                        val layoutManager = LinearLayoutManager(this)
                        seriesListView.layoutManager = layoutManager
                        seriesListView.setHasFixedSize(true)
                    }
                    if (seriesNewList.isEmpty()) {
                        count -= 10
                    }
                }
        }

        loadPrevious.setOnClickListener {
            if (count >= 10) {
                count -= 10
            }
            searchVal = getValIfNull()
            ApiService.service.getSeries(10, count, searchVal)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { wrapper ->
                    seriesNewList = ArrayList()
                    for (i in wrapper.data.results) {
                        seriesNewList.add(i)
                    }
                    if (!seriesNewList.isEmpty()) {
                        adapter.series = seriesNewList
                        adapter.notifyDataSetChanged()
                        seriesListView.adapter = adapter
                        val layoutManager = LinearLayoutManager(this)
                        seriesListView.layoutManager = layoutManager
                        seriesListView.setHasFixedSize(true)
                    }
                    if (seriesNewList.isEmpty()) {
                        count += 10
                    }
                }


        }
    }

    
    fun getValIfNull(): String? {
        var value: String? = seriesSearchField.text.toString()
        if (value.toString() == "") {
            value = null
        }
        return value
    }
}
