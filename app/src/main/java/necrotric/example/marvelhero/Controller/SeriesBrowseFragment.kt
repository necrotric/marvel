package necrotric.example.marvelhero.Controller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_hero_browse.view.*
import kotlinx.android.synthetic.main.activity_series_browse.*
import kotlinx.android.synthetic.main.activity_series_browse.view.*
import necrotric.example.marvelhero.Adapter.SeriesRecycleAdapter
import necrotric.example.marvelhero.Models.Series
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class SeriesBrowseFragment: Fragment() {
    lateinit var adapter: SeriesRecycleAdapter
    var seriesNewList = ArrayList<Series>()
    var count = 0
    var searchVal: String? = null
    lateinit var selectedAlphabet: String



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_series_browse, container, false)
            view.seriesBrowseLoad.setVisibility(View.INVISIBLE)


        adapter = SeriesRecycleAdapter( seriesNewList) { serieItem ->
            val serieInfo = Intent(activity, SeriesMoreInfoActivity::class.java)
            serieInfo.putExtra("SERIE_ID", serieItem.id.toString())
            println(serieItem.id.toString())
            startActivity(serieInfo)
        }
        val alphaChar = arrayOf(
            "A",
            "B",
            "C",
            "D",
            "E",
            "F",
            "G",
            "H",
            "I",
            "J",
            "K",
            "L",
            "M",
            "N",
            "O",
            "P",
            "Q",
            "R",
            "S",
            "T",
            "U",
            "V",
            "W",
            "X",
            "Y",
            "Z"
        )
        val spinner = view.findViewById<Spinner>(R.id.seriesBrowseSpinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, alphaChar)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                @SuppressLint("CheckResult")
                override fun onItemSelected(parent: AdapterView<*>, alphaView: View?, position: Int, id: Long) {
                 view.seriesBrowseLoad.setVisibility(View.VISIBLE)
                    Toast.makeText(
                        activity,
                        getString(R.string.selected_item) + " " + alphaChar[position],
                        Toast.LENGTH_SHORT
                    ).show()
                    selectedAlphabet = alphaChar[position].toString()
                    count = 0
                    searchVal = StringConverter.getValIfNull(selectedAlphabet)
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
                            seriesBrowseList.adapter = adapter
                            val layoutManager = LinearLayoutManager(activity)
                            seriesBrowseList.layoutManager = layoutManager
                            seriesBrowseList.setHasFixedSize(true)
                            view.seriesBrowseLoad.setVisibility(View.INVISIBLE)
                        }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
            //PAGEINATION LOAD NEXT 10
            view.seriesBrowseNext.setOnClickListener {
                view.seriesBrowseLoad.setVisibility(View.VISIBLE)
                searchVal = StringConverter.getValIfNull(selectedAlphabet)
                count += 10
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
                            seriesBrowseList.adapter = adapter
                            val layoutManager = LinearLayoutManager(activity)
                            seriesBrowseList.layoutManager = layoutManager
                            seriesBrowseList.setHasFixedSize(true)
                        }
                        if (seriesNewList.isEmpty()) {
                            count -= 10
                        }
                        view.seriesBrowseLoad.setVisibility(View.INVISIBLE)
                    }


            }
            //PAGEINATION LOAD PREVIOUS 10
            view.seriesBrowsePrevious.setOnClickListener {
                view.seriesBrowseLoad.setVisibility(View.VISIBLE)
                searchVal = StringConverter.getValIfNull(selectedAlphabet)
                if (count >= 10) {
                    count -= 10
                }
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
                            seriesBrowseList.adapter = adapter
                            val layoutManager = LinearLayoutManager(activity)
                            seriesBrowseList.layoutManager = layoutManager
                            seriesBrowseList.setHasFixedSize(true)
                        }
                        if (seriesNewList.isEmpty()) {
                            count += 10
                        }
                        view.seriesBrowseLoad.setVisibility(View.INVISIBLE)

                    }
            }



        }



        return view
}
}