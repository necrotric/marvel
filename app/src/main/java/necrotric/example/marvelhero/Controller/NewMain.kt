package necrotric.example.marvelhero.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import necrotric.example.marvelhero.R

class NewMain : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        navigateTo(MainMenuFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
       when (item.itemId){
           R.id.hero_search -> { navigateTo(HeroSearchFragment())}
           R.id.hero_browse ->{ navigateTo(HeroBrowseFragment())}
           R.id.series_search -> {navigateTo(SeriesSearchFragment())}
           R.id.series_browse ->{ navigateTo(SeriesBrowseFragment())}
       }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

     fun navigateTo(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_content, fragment)
         transaction.addToBackStack(null)
        transaction.commit()
    }

}
