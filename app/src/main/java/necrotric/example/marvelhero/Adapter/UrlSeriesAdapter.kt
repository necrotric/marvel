package necrotric.example.marvelhero.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import necrotric.example.marvelhero.R
import necrotric.example.marvelhero.Models.Series

class UrlSeriesAdapter(context: Context, serie: Series) : BaseAdapter() {
    val context = context
    val serie = serie
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val serieView: View
        val holder: ViewHolder
        if (convertView == null) {
            serieView = LayoutInflater.from(context).inflate(R.layout.url_links, null)
            holder = ViewHolder()
            holder.linkType = serieView.findViewById(R.id.linkType)
            holder.linkUrl = serieView.findViewById(R.id.linkUrl)

            serieView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            serieView = convertView
        }
        val url = serie.urls[position]

        holder.linkType!!.text = url.type.toString()
        holder.linkUrl!!.text = url.url.toString()

        return serieView

    }

    override fun getItem(position: Int): Any {
        return serie.urls[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return serie.urls.count()
    }

    private class ViewHolder {
        var linkType: TextView? = null
        var linkUrl: TextView? = null
    }

}
//}
