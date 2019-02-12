package necrotric.example.marvelhero.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.R

class HeroRecycleAdapter( var heroes: ArrayList<Hero>, val itemClick: (Hero)-> Unit ) : RecyclerView.Adapter<HeroRecycleAdapter.Holder>() {
    override fun onBindViewHolder(holder: Holder, position: Int) {
       holder.bindHero(heroes[position])
    }

    override fun getItemCount(): Int {
        return heroes.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hero_list_item, parent, false)
        return Holder(view, itemClick)
    }

    inner class Holder(itemView: View, val itemClick: (Hero) -> Unit ) : RecyclerView.ViewHolder(itemView){

        val heroDescription = itemView.findViewById<TextView>(R.id.heroListDescrption)
         val heroName = itemView.findViewById<TextView>(R.id.heroListName)
        val heroImage = itemView.findViewById<ImageView>(R.id.heroListImage)

        fun bindHero(hero: Hero){
            heroName.text = hero.name
            heroDescription.text = hero.description

            val extension = hero.thumbnail.extension.toString()
            val path = hero.thumbnail.path.toString()
            val aspectRatio = "portrait_xlarge"
            val imageBuild = path+"/"+aspectRatio+"."+extension
            Picasso.get().load(imageBuild).into(heroImage)
            itemView.setOnClickListener { itemClick(hero) }
        }
    }
}