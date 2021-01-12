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
import co.instaflix.home.model.Series
import co.instaflix.utils.loadImage

class ContentSeriesAdapter(
    var context: Context,
    var list: ArrayList<Series>,
    var listener: ContentSeriesInterface,
    var isAnimated: Boolean
) : RecyclerView.Adapter<ContentSeriesAdapter.ViewHolder>() {

    var DURATION: Long = 500
    private val onAttached = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_content_list_serie, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contentItemData = list[position]
        holder.tvNameContent.text = contentItemData.name
        holder.itemView.setOnClickListener { listener.onItemSelect(contentItemData) }
        holder.ivFlyer.loadImage(contentItemData.posterPath)
        if (isAnimated) setAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvNameContent: TextView = itemview.findViewById(R.id.tvNameContent)
        val ivFlyer: ImageView = itemview.findViewById(R.id.ivFlyer)

    }

    private fun setAnimation(itemView: View, pos: Int) {
        var i = pos
        if (!onAttached) {
            i = -1
        }
        val isNotFirstItem = i == -1
        i++
        itemView.alpha = 0f
        val animatorSet = AnimatorSet()
        val animator = ObjectAnimator.ofFloat(itemView, "alpha", 0f, 0.5f, 1.0f)
        ObjectAnimator.ofFloat(itemView, "alpha", 0f).start()
        animator.setStartDelay(if (isNotFirstItem) DURATION / 2 else i * DURATION / 3)
        animator.duration = 500
        animatorSet.play(animator)
        animator.start()
    }

}
