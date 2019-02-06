package necrotric.example.marvelhero.Controller


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
    var count = 0
    //    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        super.onSaveInstanceState(outState, outPersistentState)
//
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var heroSearch = mainSearchField.text.toString()
        mainSearchBtn.setOnClickListener {
            count = 0
            characterList = heroApiMethod(count)
                if (characterList.size > 1) {

                    adapter = HeroRecycleAdapter(this, characterList){heroitem->
                       val heroInfo =  Intent(this, HeroMoreInfo::class.java)
                        heroInfo.putExtra("SEARCH_VALUE", heroitem.id.toString())
                       startActivity(heroInfo)


                    }
                    heroListView.adapter = adapter
                    val layoutManager = LinearLayoutManager(this)
                    heroListView.layoutManager = layoutManager
                    heroListView.setHasFixedSize(true)

//                    heroListView.setOnItemClickListener { parent, view, position, id ->
//                        val pos = characterList[position].id.toString()
//                        val heroInfo = Intent(this, HeroMoreInfo::class.java)
//                        heroInfo.putExtra("SEARCH_VALUE", pos)
//                        startActivity(heroInfo);
//                        println(pos.toString())
//                    }
            }
        }
        mainNextBtn.setOnClickListener{
            count += 10
            characterList = heroApiMethod(count)
            if (characterList.size > 1) {

                adapter = HeroRecycleAdapter(this, characterList){heroitem->
                    val heroInfo =  Intent(this, HeroMoreInfo::class.java)
                    heroInfo.putExtra("SEARCH_VALUE", heroitem.id.toString())
                    startActivity(heroInfo)


                }
                heroListView.adapter = adapter
                val layoutManager = LinearLayoutManager(this)
                heroListView.layoutManager = layoutManager
                heroListView.setHasFixedSize(true)

//                heroListView.setOnItemClickListener { parent, view, position, id ->
//                    val pos = characterList[position].id.toString()
//                    val heroInfo = Intent(this, HeroMoreInfo::class.java)
//                    heroInfo.putExtra("SEARCH_VALUE", pos)
//                    startActivity(heroInfo);
//                    println(pos.toString())
//                }
            } else {
            count-=10
        }
        }
        mainBackBtn.setOnClickListener{
            if(count>=10){
                count -= 10
            }

            characterList = heroApiMethod(count)
            if (characterList.size > 1) {

                adapter = HeroRecycleAdapter(this, characterList){heroitem->
                    val heroInfo =  Intent(this, HeroMoreInfo::class.java)
                    heroInfo.putExtra("SEARCH_VALUE", heroitem.id.toString())
                    startActivity(heroInfo)
                }
                heroListView.adapter = adapter
                val layoutManager = LinearLayoutManager(this)
                heroListView.layoutManager = layoutManager
                heroListView.setHasFixedSize(true)

//                heroListView.setOnItemClickListener { parent, view, position, id ->
//                    val pos = characterList[position].id.toString()
//                    val heroInfo = Intent(this, HeroMoreInfo::class.java)
//                    heroInfo.putExtra("SEARCH_VALUE", pos)
//                    startActivity(heroInfo);
//                    println(pos.toString())
//                }
            } else {
                count+=10
            }
        }

    }

    fun heroApiMethod(count: Int): ArrayList<Hero> {
//        var firstLetter = mainSearchField.text.toString()
        var newCharacterList = ArrayList<Hero>()
        newCharacterList = ArrayList()
//        if(mainSearchField.text.toString() == "" && !characterList.isEmpty()){
//            println("First character ? "+characterList[0].name.first())
//            firstLetter = characterList[0].name.first().toString()
//        }
        val heroes = ApiService.heroApiRequest(mainSearchField.text.toString(),count)
        println(heroes.isNullOrEmpty())
        if (heroes != null) {
            println("Heroes exist")
            for (h in heroes!!) {
                h as Hero
                newCharacterList.add(h)
            }
        }
        return newCharacterList
    }

}


//Take the path element: http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73
//Select an image variant name (see the full list below) and append the variant name to the path element: http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73/portrait_xlarge
//Append the extension: http://i.annihil.us/u/prod/marvel/i/mg/3/40/4bb4680432f73/portrait_xlarge.jpg
//In order to make your web site or application load and respond quickly and preserve end-user bandwidth, we recommend using the smallest-sized image necessary to meet the needs our user interface.