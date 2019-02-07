package necrotric.example.marvelhero.Controller

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
//import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_hero_more_info.*
import necrotric.example.marvelhero.Adapter.UrlAdapter
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.Models.Items
import necrotric.example.marvelhero.Models.Urls
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class HeroMoreInfo : AppCompatActivity() {
    lateinit var adapter: UrlAdapter
    lateinit var secondAdapter: ArrayAdapter<Items>
    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_more_info)
        val oldID = intent.getStringExtra("SEARCH_VALUE")

        println("Make sure i get the old ID " + oldID)

//        heroTextName.text = oldID.toString()


//        val heroes = ApiService.oneHero(oldID.toInt())
            ApiService.service.getCharactersById(oldID.toInt())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { wrapper ->
                   for(h in wrapper.data.results){
                       //                h as Hero

                val urlLink= makeUrlPath(h)

                heroTextName.text = h.name
                heroTextDescription.text = h.description
                Picasso.get().load(urlLink.toString()).into(heroInfoImage)
//                println("Does this work?" + h.name)
//                println("Does this work?" + h.description)
//                println("Does this work?" + h.urls[])
//                println("Does this work?" + h.description)

                adapter = UrlAdapter(this,h)
                urlHeroList.adapter = adapter

                secondAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, h.series.items)
                heroListAllSeries.adapter = secondAdapter
                   }
                }


//            for(h in heroes!!){
//
//                h as Hero
//
//                val urlLink= makeUrlPath(h)
//
//                heroTextName.text = h.name
//                heroTextDescription.text = h.description
//                Picasso.get().load(urlLink.toString()).into(heroInfoImage)
//                println("Does this work?" + h.name)
//                println("Does this work?" + h.description)
////                println("Does this work?" + h.urls[])
//                println("Does this work?" + h.description)

//                adapter = UrlAdapter(this,h)
//                urlHeroList.adapter = adapter
//
//                secondAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, h.series.items)
//                heroListAllSeries.adapter = secondAdapter
//         }

    }

    fun makeUrlPath(hero: Hero): String{
        val extension = hero.thumbnail.extension.toString()
        val path = hero.thumbnail.path.toString()
        val aspectRatio = "portrait_fantastic"
        val imageBuild = path+"/"+aspectRatio+"."+extension
        println("Here is imagebuild " +imageBuild)
        return imageBuild
    }


}

