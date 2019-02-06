package necrotric.example.marvelhero.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_hero_browse.*
import necrotric.example.marvelhero.Adapter.HeroAdapter
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService


class HeroBrowseActivity : AppCompatActivity() {

    lateinit var adapter: HeroAdapter
    var characterList = ArrayList<Hero>()
    var count = 0
    lateinit var selectedAlphabet: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_browse)

        val personNames = arrayOf(
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
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, personNames)
            spinner.adapter = arrayAdapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Toast.makeText(
                        this@HeroBrowseActivity,
                        getString(R.string.selected_item) + " " + personNames[position],
                        Toast.LENGTH_SHORT
                    ).show()
                    selectedAlphabet = personNames[position].toString()
                    count = 0
                    characterList = heroApiMethod(count)
                    if (characterList.size > 1) {

                        adapter = HeroAdapter(this@HeroBrowseActivity, characterList)

                        browseHeroListView.adapter = adapter
                        browseHeroListView.setOnItemClickListener { parent, view, position, id ->
                            val pos = characterList[position].id.toString()
                        val heroInfo = Intent(this@HeroBrowseActivity,HeroMoreInfo::class.java)
                        heroInfo.putExtra("SEARCH_VALUE", pos)
                        startActivity(heroInfo);
                        println(pos.toString())
                        }


                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }


            heroBrowseNextBtn.setOnClickListener {
                count += 10
                characterList = heroApiMethod(count)
                if (characterList.size > 1) {

                    adapter = HeroAdapter(this@HeroBrowseActivity, characterList)

                    browseHeroListView.adapter = adapter
                    browseHeroListView.setOnItemClickListener { parent, view, position, id ->
                        val pos = characterList[position].id.toString()
                        val heroInfo = Intent(this@HeroBrowseActivity,HeroMoreInfo::class.java)
                        heroInfo.putExtra("SEARCH_VALUE", pos)
                        startActivity(heroInfo);
                    }


                }else {
                    count-=10
                }
            }
            heroBrowseBackBtn.setOnClickListener {
                if(count>=10){
                    count -= 10
                }
                characterList = heroApiMethod(count)
                if (characterList.size > 1) {
                    adapter = HeroAdapter(this@HeroBrowseActivity, characterList)
                    browseHeroListView.adapter = adapter
                    browseHeroListView.setOnItemClickListener { parent, view, position, id ->
                        val pos = characterList[position].id.toString()
                        val heroInfo = Intent(this@HeroBrowseActivity,HeroMoreInfo::class.java)
                        heroInfo.putExtra("SEARCH_VALUE", pos)
                        startActivity(heroInfo);
                    }
                }else {
                    count+=10
                }
            }
        }


    }
    fun heroApiMethod(count: Int): ArrayList<Hero> {
        var newCharacterList = ArrayList<Hero>()
        newCharacterList = ArrayList()


        val heroes = ApiService.heroApiRequest(selectedAlphabet.toString(),count)
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
