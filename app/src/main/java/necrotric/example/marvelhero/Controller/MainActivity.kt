package necrotric.example.marvelhero.Controller


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

import necrotric.example.marvelhero.Adapter.HeroAdapter
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.Models.Urls
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

//MAIN TODO make navbar before this (currently hero search)


class MainActivity : AppCompatActivity() {
   lateinit var adapter : HeroAdapter
    var characterList = ArrayList<Hero>()
    //    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        super.onSaveInstanceState(outState, outPersistentState)
//
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        mainSearchBtn.setOnClickListener {
            characterList.removeAll(characterList)
            characterList = ArrayList()
            val heroSearch = mainSearchField.text.toString()

            val heroes = ApiService.heroApiRequest(heroSearch.toString())
            println(heroes.isNullOrEmpty())
            if(heroes != null){
                println("heroes is nothing")

            for (h in heroes!!) {
                h as Hero
              //  println("URL" + h.urls[0].url.toString())

//                for(links in h.urls){
//                    println("Type: "+ links.type  + "Link: " + links.url)
//                }

                characterList.add(h)

            }
            for(anotherHero in characterList){
                println("Name: " + anotherHero.thumbnail.extension)

//                println("Series: " +anotherHero.series.items.size)
//                for(comics in anotherHero.series.items){
//                    println("Series name:                 " + comics.name)
//                }

            }
            if(characterList.size>1){

                adapter = HeroAdapter(this, characterList)
                heroListView.adapter = adapter
                
                heroListView.setOnItemClickListener { parent, view, position, id ->
                    val random = characterList[position].id.toString()
                    val heroInfo = Intent(this, HeroMoreInfo::class.java)
                    heroInfo.putExtra("SEARCH_VALUE", random)
                    startActivity(heroInfo);
                    println(random.toString())
                }
            }
            }
        }
//
//
//        println("CHARACTER SIZE OUTSIDE OF everything " + characterList.size)
//
//
//


    }
}

//Take the path element: http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73
//Select an image variant name (see the full list below) and append the variant name to the path element: http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73/portrait_xlarge
//Append the extension: http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73/portrait_xlarge.jpg
//In order to make your web site or application load and respond quickly and preserve end-user bandwidth, we recommend using the smallest-sized image necessary to meet the needs our user interface.