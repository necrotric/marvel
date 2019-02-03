package necrotric.example.marvelhero.Adapter

import android.annotation.SuppressLint
import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.R
import android.graphics.BitmapFactory
import com.squareup.picasso.Picasso

import java.net.URL


class HeroAdapter(context: Context, heroes: ArrayList<Hero>) : BaseAdapter() {
    val context = context
    val heroes = heroes
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val heroView: View
        heroView = LayoutInflater.from(context).inflate(R.layout.hero_list_item, null)
//        val heroImage : ImageView = heroView.findViewById(R.id.heroListImage)
        val heroDescription : TextView = heroView.findViewById(R.id.heroListDescrption)
        val heroName : TextView = heroView.findViewById(R.id.heroListName)
        val heroImage : ImageView = heroView.findViewById(R.id.heroListImage)

        val hero = heroes[position]

        val extension = hero.thumbnail.extension.toString()
        val path = hero.thumbnail.path.toString()
        val aspectRatio = "portrait_xlarge"

        heroName.text = hero.name
        heroDescription.text = hero.description
        val imageBuild = path+"/"+aspectRatio+"."+extension
        Picasso.get().load(imageBuild).into(heroImage)

//        val newurl = URL(imageBuild)
//        val mIconval = BitmapFactory.decodeStream(newurl.openConnection().getInputStream())
//        heroImage.setImageBitmap(mIconval)
        println(imageBuild)

        return heroView
    }


    override fun getItem(position: Int): Any {
      return heroes[position]
    }

    override fun getItemId(position: Int): Long {
      return 0
    }

    override fun getCount(): Int {
        return heroes.count()
    }
}