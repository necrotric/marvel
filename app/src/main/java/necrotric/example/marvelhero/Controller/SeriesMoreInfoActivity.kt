package necrotric.example.marvelhero.Controller

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_series_more_info.*
import necrotric.example.marvelhero.Adapter.UrlSeriesAdapter
import necrotric.example.marvelhero.Models.Items
import necrotric.example.marvelhero.Models.Series

import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Services.ApiService

class SeriesMoreInfoActivity : AppCompatActivity() {
    lateinit var adapter: UrlSeriesAdapter
     lateinit var secondAdapter: ArrayAdapter<Items>

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_series_more_info)

        val oldID = intent.getStringExtra("SERIE_ID")
        println("Make sure i get the old ID " + oldID)

        ApiService.service.getSerieById(oldID.toInt())
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { wrapper ->
                for(serie in wrapper.data.results){
                    val urlLink= makeUrlPath(serie)

                serieMoreInfoName.text = serie.title
                serieMoreInfoDescription.text = serie.description
                    if(serie.rating!="" || serie.rating!=null){
                        serieRating.text = ""
                    }
                    serieMoreInfoRating.text= serie.rating
                Picasso.get().load(urlLink.toString()).into(seriesMoreInfoImage)

                adapter = UrlSeriesAdapter(this,serie)
                urlSerieList.adapter = adapter

                secondAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, serie.characters.items)
                seriesListAllHeroes.adapter = secondAdapter
//
                }
            }
    }
  fun makeUrlPath(serie: Series): String{
        val extension = serie.thumbnail.extension.toString()
        val path = serie.thumbnail.path.toString()
        val aspectRatio = "portrait_fantastic"
        val imageBuild = path+"/"+aspectRatio+"."+extension
        println("Here is imagebuild " +imageBuild)
        return imageBuild
    }
}
