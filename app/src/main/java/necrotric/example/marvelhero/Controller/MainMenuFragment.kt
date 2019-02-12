package necrotric.example.marvelhero.Controller
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.activity_main_menu.view.*
import necrotric.example.marvelhero.R

class MainMenuFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_main_menu, container, false)


        view.menuHeroSrchBtn.setOnClickListener {
            val newMainActivity = activity as NewMain
            newMainActivity.navigateTo(HeroSearchFragment())

        }
        view.menuHeroBrwseBtn.setOnClickListener {
            val newMainActivity = activity as NewMain
            newMainActivity.navigateTo(HeroBrowseFragment())

        }
        view.menuSerieSrchBtn.setOnClickListener {
            val newMainActivity = activity as NewMain
            newMainActivity.navigateTo(SeriesSearchFragment())
        }
        view.menuSerieBrwseBtn.setOnClickListener {
            val newMainActivity = activity as NewMain
            newMainActivity.navigateTo(SeriesBrowseFragment())
        }

        return view
}
}
