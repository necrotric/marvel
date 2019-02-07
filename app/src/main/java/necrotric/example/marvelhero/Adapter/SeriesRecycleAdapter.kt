package necrotric.example.marvelhero.Adapter



import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import necrotric.example.marvelhero.Models.Series
import necrotric.example.marvelhero.R

class SeriesRecycleAdapter(val context: Context, val series: ArrayList<Series>, val itemClick: (Series)-> Unit ) : RecyclerView.Adapter<SeriesRecycleAdapter.Holder>() {
    override fun onBindViewHolder(holder: Holder, position: Int) {
       holder.bindSerie(series[position],context)
    }

    override fun getItemCount(): Int {
        return series.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.serie_list_item, parent, false)
        return Holder(view, itemClick)
    }

    inner class Holder(itemView: View, val itemClick: (Series) -> Unit ) : RecyclerView.ViewHolder(itemView){

        val serieDescription = itemView.findViewById<TextView>(R.id.serieListDescription)
         val serieName = itemView.findViewById<TextView>(R.id.serieListName)
        val serieImage = itemView.findViewById<ImageView>(R.id.serieListImage)

        fun bindSerie(serie: Series, context: Context){
            serieName.text = serie.title
            serieDescription.text = serie.description

            val extension = serie.thumbnail.extension.toString()
            val path = serie.thumbnail.path.toString()
            val aspectRatio = "portrait_xlarge"
            val imageBuild = path+"/"+aspectRatio+"."+extension
            Picasso.get().load(imageBuild).into(serieImage)
            itemView.setOnClickListener { itemClick(serie) }
        }
    }
}