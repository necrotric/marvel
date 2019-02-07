package necrotric.example.marvelhero.Controller


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

import necrotric.example.marvelhero.Adapter.HeroAdapter
import necrotric.example.marvelhero.Adapter.HeroRecycleAdapter
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

//MAIN TODO make navbar before this (currently hero search)


class MainActivity : AppCompatActivity() {
    lateinit var adapter: HeroRecycleAdapter
    var characterList = ArrayList<Hero>()
    var searchVal: String? = null
    var count = 0
    //    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        super.onSaveInstanceState(outState, outPersistentState)
//
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = HeroRecycleAdapter(this, characterList) { heroitem ->
            val heroInfo = Intent(this, HeroMoreInfo::class.java)
            heroInfo.putExtra("SEARCH_VALUE", heroitem.id.toString())
            println(heroitem.id.toString())
            startActivity(heroInfo)
        }

        mainSearchBtn.setOnClickListener {
            count = 0
            searchVal = getValIfNull()
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
                    heroListView.adapter = adapter
                    val layoutManager = LinearLayoutManager(this)
                    heroListView.layoutManager = layoutManager
                    heroListView.setHasFixedSize(true)
                }

        }
        mainNextBtn.setOnClickListener {
            count += 10
            searchVal = getValIfNull()
//            characterList = heroApiMethod(count)
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
                        heroListView.adapter = adapter
                        val layoutManager = LinearLayoutManager(this)
                        heroListView.layoutManager = layoutManager
                        heroListView.setHasFixedSize(true)
                    }
                    if (characterList.isEmpty()) {
                        count -= 10
                    }

                }
        }
        mainBackBtn.setOnClickListener {
            if (count >= 10) {
                count -= 10
            }
            searchVal = getValIfNull()
//            characterList = heroApiMethod(count)
            ApiService.service.getCharacters(searchVal, count, 10)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { wrapper ->
                    characterList = ArrayList()
                    for (i in wrapper.data.results) {
                        i as Hero
                        characterList.add(i)
                    }
                    if (characterList.isEmpty()) {
                        count += 10
                        println("im empty as fuck")
                    }
                    if (!characterList.isEmpty()) {
                        adapter.heroes = characterList
                        adapter.notifyDataSetChanged()
                        heroListView.adapter = adapter
                        val layoutManager = LinearLayoutManager(this)
                        heroListView.layoutManager = layoutManager
                        heroListView.setHasFixedSize(true)
                    }


                }
        }

    }

    fun getValIfNull(): String? {
        var value: String? = mainSearchField.text.toString()
        if (value.toString() == "") {
            value = null
        }
        return value
    }
}


//Take the path element: http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73
//Select an image variant name (see the full list below) and append the variant name to the path element: http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73/portrait_xlarge
//Append the extension: http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73/portrait_xlarge.jpg
//In order to make your web site or application load and respond quickly and preserve end-user bandwidth, we recommend using the smallest-sized image necessary to meet the needs our user interface.