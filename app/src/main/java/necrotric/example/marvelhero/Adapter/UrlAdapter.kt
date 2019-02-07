package necrotric.example.marvelhero.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import necrotric.example.marvelhero.Models.Hero
import necrotric.example.marvelhero.R

class UrlAdapter(context: Context, hero: Hero) : BaseAdapter() {

    val context = context
    val hero = hero
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       val heroView: View
        val holder : ViewHolder
        if(convertView == null) {
            heroView = LayoutInflater.from(context).inflate(R.layout.url_links, null)
            holder = ViewHolder()
            holder.linkType = heroView.findViewById(R.id.linkType)
            holder.linkUrl = heroView.findViewById(R.id.linkUrl)

            heroView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            heroView = convertView
        }
        val url = hero.urls[position]

        holder.linkType!!.text = url.type.toString()
        holder.linkUrl!!.text = url.url.toString()

        return heroView

    }

    override fun getItem(position: Int): Any {
        return hero.urls[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return hero.urls.count()
    }
    private class ViewHolder{
      var linkType: TextView? = null
      var linkUrl:TextView? = null
 }
}
