package necrotric.example.marvelhero.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_hero_more_info.*
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.Models.Heroes
import necrotric.example.marvelhero.Models.Items
import necrotric.example.marvelhero.Models.Urls
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class HeroMoreInfo : AppCompatActivity() {
    lateinit var adapter: ArrayAdapter<Urls>
    lateinit var secondAdapter: ArrayAdapter<Items>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_more_info)
        val oldID = intent.getStringExtra("SEARCH_VALUE")

        println("Make sure i get the old ID " + oldID)

        oldInformation.text = oldID.toString()

        val heroes = ApiService.oneHero(oldID.toInt())
        println(heroes.isNullOrEmpty())


            for(h in heroes!!){
                h as Hero

                println("Does this work?" + h.name)
                println("Does this work?" + h.description)
//                println("Does this work?" + h.urls[])
                println("Does this work?" + h.description)

                adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,h.urls)
                urlHeroList.adapter = adapter

                secondAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, h.series.items)
                heroListAllSeries.adapter = secondAdapter
         }

    }


}

