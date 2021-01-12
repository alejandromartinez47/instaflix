package co.instaflix.home.adapter


import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.instaflix.R
import co.instaflix.home.model.Movies
import co.instaflix.utils.loadImage

class ContentMoviesAdapter(
    var context: Context,
    var list: ArrayList<Movies>,
    var listener: ContentMoviesInterface
) : RecyclerView.Adapter<ContentMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_content_list_movie, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contentItemData = list[position]
        holder.tvNameContent.text = contentItemData.title
        holder.itemView.setOnClickListener { listener.onItemSelect(contentItemData) }
        holder.ivFlyer.loadImage(contentItemData.posterPath)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvNameContent: TextView = itemview.findViewById(R.id.tvNameContent)
        val ivFlyer: ImageView = itemview.findViewById(R.id.ivFlyer)

    }


}
