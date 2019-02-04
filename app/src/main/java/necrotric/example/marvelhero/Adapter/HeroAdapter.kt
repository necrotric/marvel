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
        val holder: ViewHolder

        if(convertView == null){
            heroView = LayoutInflater.from(context).inflate(R.layout.hero_list_item, null)
            holder = ViewHolder()
            holder.heroDescription = heroView.findViewById(R.id.heroListDescrption)
            holder.heroName = heroView.findViewById(R.id.heroListName)
            holder.heroImage = heroView.findViewById(R.id.heroListImage)

            heroView.tag = holder
            println("I Exist the first time")
        } else {
            holder = convertView.tag as ViewHolder
            heroView = convertView
            println("Recycling view")
        }



        val hero = heroes[position]

        val extension = hero.thumbnail.extension.toString()
        val path = hero.thumbnail.path.toString()
        val aspectRatio = "portrait_xlarge"

        holder.heroName?.text = hero.name
        holder.heroDescription?.text = hero.description
        val imageBuild = path+"/"+aspectRatio+"."+extension
        Picasso.get().load(imageBuild).into(holder.heroImage)

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
    private class ViewHolder{
        var heroDescription: TextView? = null
        var heroName: TextView? = null
        var heroImage: ImageView? = null

    }

}