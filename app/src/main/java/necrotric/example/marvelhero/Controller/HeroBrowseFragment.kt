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
import kotlinx.android.synthetic.main.activity_hero_browse.*
import kotlinx.android.synthetic.main.activity_hero_browse.view.*
import necrotric.example.marvelhero.Adapter.HeroRecycleAdapter
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class HeroBrowseFragment: Fragment() {

    lateinit var adapter: HeroRecycleAdapter
    var characterList = ArrayList<Hero>()
    var count = 0
    var searchVal: String? = null
    lateinit var selectedAlphabet: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_hero_browse, container, false)

        view.heroBrowseSpinner.setVisibility(View.INVISIBLE)

        adapter = HeroRecycleAdapter( characterList) { heroitem ->
            val heroInfo = Intent(activity, HeroMoreInfo::class.java)
            heroInfo.putExtra("SEARCH_VALUE", heroitem.id.toString())
            println(heroitem.id.toString())
            startActivity(heroInfo)
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
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, alphaChar)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                @SuppressLint("CheckResult")
                override fun onItemSelected(parent: AdapterView<*>, heroBrowseView: View, position: Int, id: Long) {
                    view.heroBrowseSpinner.setVisibility(View.VISIBLE)
                    Toast.makeText(
                        activity,
                        getString(R.string.selected_item) + " " + alphaChar[position],
                        Toast.LENGTH_SHORT
                    ).show()
                    selectedAlphabet = alphaChar[position].toString()
                    count = 0
                    searchVal = StringConverter.getValIfNull(selectedAlphabet)
                    ApiService.service.getCharacters(searchVal, count, 10)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { wrapper ->
                            characterList = ArrayList()
                            for (i in wrapper.data.results) {
                                characterList.add(i)
                            }
                            adapter.heroes = characterList
                            adapter.notifyDataSetChanged()
                            browseHeroListView.adapter = adapter
                            val layoutManager = LinearLayoutManager(activity)
                            browseHeroListView.layoutManager = layoutManager
                            browseHeroListView.setHasFixedSize(true)
                            view.heroBrowseSpinner.setVisibility(View.INVISIBLE)
                        }

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }


            view.heroBrowseNextBtn.setOnClickListener {
                view.heroBrowseSpinner.setVisibility(View.VISIBLE)
                searchVal = StringConverter.getValIfNull(selectedAlphabet)
                count += 10
                ApiService.service.getCharacters(searchVal, count, 10)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { wrapper ->
                        characterList = ArrayList()
                        for (i in wrapper.data.results) {
                            characterList.add(i)
                        }
                        if (!characterList.isEmpty()) {
                            adapter.heroes = characterList
                            adapter.notifyDataSetChanged()
                            browseHeroListView.adapter = adapter
                            val layoutManager = LinearLayoutManager(activity)
                            browseHeroListView.layoutManager = layoutManager
                            browseHeroListView.setHasFixedSize(true)
                        }
                        if (characterList.isEmpty()) {
                            count -= 10
                        }
                        view.heroBrowseSpinner.setVisibility(View.INVISIBLE)
                    }
            }
            view.heroBrowseBackBtn.setOnClickListener {
                view.heroBrowseSpinner.setVisibility(View.VISIBLE)
                searchVal = StringConverter.getValIfNull(selectedAlphabet)
                if(count>=10){
                    count -= 10
                }
                ApiService.service.getCharacters(searchVal, count, 10)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { wrapper ->
                        characterList = ArrayList()
                        for (i in wrapper.data.results) {
                            characterList.add(i)
                        }
                        if (characterList.isEmpty()) {
                            count += 10
                            println("im empty as fuck")
                        }
                        if (!characterList.isEmpty()) {
                            adapter.heroes = characterList
                            adapter.notifyDataSetChanged()
                            browseHeroListView.adapter = adapter
                            val layoutManager = LinearLayoutManager(activity)
                            browseHeroListView.layoutManager = layoutManager
                            browseHeroListView.setHasFixedSize(true)
                        }

                        view.heroBrowseSpinner.setVisibility(View.INVISIBLE)
                    }
            }
        }


        return view
    }
}