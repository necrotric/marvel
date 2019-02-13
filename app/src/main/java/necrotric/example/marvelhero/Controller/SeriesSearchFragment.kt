package necrotric.example.marvelhero.Controller

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_series_search.*
import kotlinx.android.synthetic.main.activity_series_search.view.*

import necrotric.example.marvelhero.Adapter.SeriesRecycleAdapter
import necrotric.example.marvelhero.Models.Series
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class SeriesSearchFragment: Fragment() {
    lateinit var adapter: SeriesRecycleAdapter
    var seriesNewList = ArrayList<Series>()
    var searchVal: String? = null
    var pagination = 0



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_series_search, container, false)
        view.seriesSrchSpinner.setVisibility(View.INVISIBLE)

        adapter = SeriesRecycleAdapter(seriesNewList) { serieItem ->
            val serieInfo = Intent(activity, SeriesMoreInfoActivity::class.java)
            serieInfo.putExtra("SERIE_ID", serieItem.id.toString())
            println(serieItem.id.toString())
            startActivity(serieInfo)
        }


        view.seriesSearchBtn.setOnClickListener {
            view.seriesSrchSpinner.setVisibility(View.VISIBLE)
            pagination = 0
            searchVal = StringConverter.getValIfNull(view.seriesSearchField.text.toString())
            ApiService.service.getSeries(10, pagination, searchVal)
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
                    val layoutManager = LinearLayoutManager(activity)
                    seriesListView.layoutManager = layoutManager
                    seriesListView.setHasFixedSize(true)
                    view.seriesSrchSpinner.setVisibility(View.INVISIBLE)
                }

        }
        view.loadMore.setOnClickListener {
            view.seriesSrchSpinner.setVisibility(View.VISIBLE)
            pagination += 10
            searchVal = StringConverter.getValIfNull(view.seriesSearchField.text.toString())
            ApiService.service.getSeries(10, pagination, searchVal)
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
                        val layoutManager = LinearLayoutManager(activity)
                        seriesListView.layoutManager = layoutManager
                        seriesListView.setHasFixedSize(true)
                    }
                    if (seriesNewList.isEmpty()) {
                        pagination -= 10
                    }
                    view.seriesSrchSpinner.setVisibility(View.INVISIBLE)
                }
        }

        view.loadPrevious.setOnClickListener {
            view.seriesSrchSpinner.setVisibility(View.VISIBLE)
            if (pagination >= 10) {
                pagination -= 10
            }
            searchVal = StringConverter.getValIfNull(view.seriesSearchField.text.toString())
            ApiService.service.getSeries(10, pagination, searchVal)
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
                        val layoutManager = LinearLayoutManager(activity)
                        seriesListView.layoutManager = layoutManager
                        seriesListView.setHasFixedSize(true)
                    }
                    if (seriesNewList.isEmpty()) {
                        pagination += 10
                    }
                    view.seriesSrchSpinner.setVisibility(View.INVISIBLE)
                }


        }

        return view
}
}