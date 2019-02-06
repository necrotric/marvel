package necrotric.example.marvelhero.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_menu.*
import necrotric.example.marvelhero.R

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)


        menuHeroSrchBtn.setOnClickListener {
            val mainA = Intent(this, MainActivity::class.java)
                   startActivity(mainA);

        }
        menuHeroBrwseBtn.setOnClickListener {
            val heroBrowse = Intent(this, HeroBrowseActivity::class.java)
            startActivity(heroBrowse);

        }
        menuSerieSrchBtn.setOnClickListener {
            val serieSearchActivity = Intent(this, SeriesSearchActivity::class.java)
            startActivity(serieSearchActivity)
        }
        menuSerieBrwseBtn.setOnClickListener {
            //TODO to series browse activity
        }
    }
}
