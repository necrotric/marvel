package necrotric.example.marvelhero.Controller

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
//                   thread(start = true) {

                    characterList.removeAll(characterList)
                    characterList = ArrayList()
                    val heroes = ApiService.heroApiRequest(personNames[position].toString())
                    if (heroes != null) {
                        for (h in heroes!!) {
                            h as Hero
//                            println("descrption" + h.description)
                            characterList.add(h)
                        }
                        for (anotherHero in characterList) {
//                            println("Name: " + anotherHero.thumbnail.extension)

//                println("Series: " +anotherHero.series.items.size)
//                for(comics in anotherHero.series.items){
//                    println("Series name:                 " + comics.name)
//                }

                        }

                    }
                    if (characterList.size > 1) {

                        adapter = HeroAdapter(this@HeroBrowseActivity, characterList)

                        heroListView.adapter = adapter


                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }


    }


}
