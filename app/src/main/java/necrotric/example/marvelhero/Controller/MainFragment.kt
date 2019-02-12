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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import necrotric.example.marvelhero.Adapter.HeroRecycleAdapter
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class MainFragment: Fragment() {
    lateinit var adapter: HeroRecycleAdapter
    var characterList = ArrayList<Hero>()
    var searchVal: String? = null
    var count = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_main, container, false)

        adapter = HeroRecycleAdapter(characterList) { heroitem ->
            val heroInfo = Intent(activity, HeroMoreInfo::class.java)
            heroInfo.putExtra("SEARCH_VALUE", heroitem.id.toString())
            println(heroitem.id.toString())
            startActivity(heroInfo)

        }

        mainSearchBtn.setOnClickListener {
            count = 0
            searchVal = StringConverter.getValIfNull(view.mainSearchField.text.toString())
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
                    val layoutManager = LinearLayoutManager(activity)
                    heroListView.layoutManager = layoutManager
                    heroListView.setHasFixedSize(true)
                }

        }

        mainNextBtn.setOnClickListener {
            count += 10
            searchVal = StringConverter.getValIfNull(view.mainSearchField.text.toString())
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
                        val layoutManager = LinearLayoutManager(activity)
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
            searchVal = StringConverter.getValIfNull(view.mainSearchField.text.toString())
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
                        val layoutManager = LinearLayoutManager(activity)
                        heroListView.layoutManager = layoutManager
                        heroListView.setHasFixedSize(true)
                    }


                }
        }
        return view
    }
}