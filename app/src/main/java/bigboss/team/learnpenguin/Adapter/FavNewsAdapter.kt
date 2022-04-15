package bigboss.team.learnpenguin.Adapter

import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import bigboss.team.learnpenguin.Interface.ItemClickListener
import bigboss.team.learnpenguin.Model.FavNewsObject
import bigboss.team.learnpenguin.Model.Item
import bigboss.team.learnpenguin.Model.RssObject
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentNewsBinding
import com.squareup.picasso.Picasso

class FavNewsViewHolder (itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener,View.OnLongClickListener
{
    var newsTitle:TextView
    var newsDate:TextView
    var newsImage:ImageView

    private var itemClickListener : ItemClickListener ?= null

    init {
        newsTitle = itemView.findViewById(R.id.favNewsTitle) as TextView
        newsDate = itemView.findViewById(R.id.favNewsDate) as TextView
        newsImage = itemView.findViewById(R.id.favNewsImage) as ImageView

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener)
    {
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener!!.onClick(v,absoluteAdapterPosition,false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemClickListener!!.onClick(v,absoluteAdapterPosition,true)
        return true
    }

}

class FavNewsAdapter (private val favNewsObjectList: ArrayList<FavNewsObject>, private val fActivity: FragmentActivity?): RecyclerView.Adapter<FavNewsViewHolder>()
{
    private val inflater:LayoutInflater

    init {
        inflater = LayoutInflater.from(fActivity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavNewsViewHolder {
        val itemView = inflater.inflate(R.layout.fav_news_row,parent,false)
        return FavNewsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavNewsViewHolder, position: Int) {
        holder.newsTitle.text = favNewsObjectList[position].title
        holder.newsDate.text = favNewsObjectList[position].pubDate
        if(favNewsObjectList[position].thumbnail != "" || favNewsObjectList[position].thumbnail != null)
            Picasso.get().load(favNewsObjectList[position].thumbnail).into(holder.newsImage)


        holder.setItemClickListener(ItemClickListener{ view, position, isLongClick ->

            if(!isLongClick)
            {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(favNewsObjectList[position].link))
                fActivity?.startActivity(browserIntent)
            }
        } )
    }

    override fun getItemCount(): Int {
        return favNewsObjectList.size
    }

}


