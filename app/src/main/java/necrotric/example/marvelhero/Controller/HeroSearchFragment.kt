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
import kotlinx.android.synthetic.main.old_activity_main.*
import kotlinx.android.synthetic.main.old_activity_main.view.*
import necrotric.example.marvelhero.Adapter.HeroRecycleAdapter
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class HeroSearchFragment: Fragment() {
    lateinit var adapter: HeroRecycleAdapter
    var characterList = ArrayList<Hero>()
    var searchVal: String? = null
    var pagination = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.old_activity_main, container, false)
        view.loadingSpinner.setVisibility(View.INVISIBLE)

        adapter = HeroRecycleAdapter(characterList) { heroitem ->
            val heroInfo = Intent(activity, HeroMoreInfo::class.java)
            heroInfo.putExtra("SEARCH_VALUE", heroitem.id.toString())
            println(heroitem.id.toString())
            startActivity(heroInfo)

        }

        view.mainSearchBtn.setOnClickListener {
            view.loadingSpinner.setVisibility(View.VISIBLE)
            pagination = 0
            searchVal = StringConverter.getValIfNull(view.mainSearchField.text.toString())
            ApiService.service.getCharacters(searchVal, pagination, 10)
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
                    view.loadingSpinner.setVisibility(View.INVISIBLE)
                }

        }

        view.mainNextBtn.setOnClickListener {
            view.loadingSpinner.setVisibility(View.VISIBLE)
            pagination += 10
            searchVal = StringConverter.getValIfNull(view.mainSearchField.text.toString())
//            characterList = heroApiMethod(pagination)
            ApiService.service.getCharacters(searchVal, pagination, 10)
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
                        pagination -= 10
                    }
                    view.loadingSpinner.setVisibility(View.INVISIBLE)
                }
        }
        view.mainBackBtn.setOnClickListener {
            view.loadingSpinner.setVisibility(View.VISIBLE)
            if (pagination >= 10) {
                pagination -= 10
            }
            searchVal = StringConverter.getValIfNull(view.mainSearchField.text.toString())
//            characterList = heroApiMethod(pagination)
            ApiService.service.getCharacters(searchVal, pagination, 10)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { wrapper ->
                    characterList = ArrayList()
                    for (i in wrapper.data.results) {
                        i as Hero
                        characterList.add(i)
                    }
                    if (characterList.isEmpty()) {
                        pagination += 10
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

                    view.loadingSpinner.setVisibility(View.INVISIBLE)
                }
        }
        return view
    }
}