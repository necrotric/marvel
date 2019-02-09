package necrotric.example.marvelhero.Controller

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_series_browse.*
import kotlinx.android.synthetic.main.activity_series_search.*
import necrotric.example.marvelhero.Adapter.SeriesRecycleAdapter
import necrotric.example.marvelhero.Models.Series
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class SeriesBrowseActivity : AppCompatActivity() {

    lateinit var adapter: SeriesRecycleAdapter
    var seriesNewList = ArrayList<Series>()
    var count = 0
    var searchVal: String? = null
    lateinit var selectedAlphabet: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_browse)

        adapter = SeriesRecycleAdapter(this, seriesNewList) { serieItem ->
            val serieInfo = Intent(this, SeriesMoreInfoActivity::class.java)
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
        val spinner = findViewById<Spinner>(R.id.seriesBrowseSpinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, alphaChar)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                @SuppressLint("CheckResult")
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    seriesBrowseLoad.setVisibility(View.INVISIBLE)
                    Toast.makeText(
                        this@SeriesBrowseActivity,
                        getString(R.string.selected_item) + " " + alphaChar[position],
                        Toast.LENGTH_SHORT
                    ).show()
                    selectedAlphabet = alphaChar[position].toString()
                    count = 0
                    searchVal = getValIfNull(selectedAlphabet)
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
                            val layoutManager = LinearLayoutManager(this@SeriesBrowseActivity)
                            seriesBrowseList.layoutManager = layoutManager
                            seriesBrowseList.setHasFixedSize(true)
                            seriesBrowseLoad.setVisibility(View.INVISIBLE)
                        }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
            //PAGEINATION LOAD NEXT 10
            seriesBrowseNext.setOnClickListener {
                seriesBrowseLoad.setVisibility(View.VISIBLE)
                searchVal = getValIfNull(selectedAlphabet)
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
                            val layoutManager = LinearLayoutManager(this)
                            seriesBrowseList.layoutManager = layoutManager
                            seriesBrowseList.setHasFixedSize(true)
                        }
                        if (seriesNewList.isEmpty()) {
                            count -= 10
                        }
                        seriesBrowseLoad.setVisibility(View.INVISIBLE)
                    }


            }
            //PAGEINATION LOAD PREVIOUS 10
            seriesBrowsePrevious.setOnClickListener {
                seriesBrowseLoad.setVisibility(View.VISIBLE)
                searchVal = getValIfNull(selectedAlphabet)
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
                            val layoutManager = LinearLayoutManager(this)
                            seriesBrowseList.layoutManager = layoutManager
                            seriesBrowseList.setHasFixedSize(true)
                        }
                        if (seriesNewList.isEmpty()) {
                            count += 10
                        }
                        seriesBrowseLoad.setVisibility(View.INVISIBLE)

                    }
            }



        }
    }
    fun getValIfNull(selectedChar: String): String? {
        var value: String? = selectedChar
        if (value.toString() == "") {
            value = null
        }
        return value
    }
}

