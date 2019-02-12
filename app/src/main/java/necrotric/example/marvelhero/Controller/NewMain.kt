package necrotric.example.marvelhero.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import necrotric.example.marvelhero.R

class NewMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)
        navigateTo(SeriesBrowseFragment())
    }
    private fun navigateTo(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_content, fragment)
        transaction.commit()
    }
}
