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
import bigboss.team.learnpenguin.Model.RssObject
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentNewsBinding
import com.squareup.picasso.Picasso

class FeedViewHolder (itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener,View.OnLongClickListener
{
    var newsTitle:TextView
    var newsDate:TextView
    var newsImage:ImageView

    private var itemClickListener : ItemClickListener ?= null

    init {
        newsTitle = itemView.findViewById(R.id.newsTitle) as TextView
        newsDate = itemView.findViewById(R.id.newsDate) as TextView
        newsImage = itemView.findViewById(R.id.newsImage) as ImageView

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

class FeedAdapter (private val rssObject: RssObject, private val fActivity: FragmentActivity?): RecyclerView.Adapter<FeedViewHolder>()
{
    private val inflater:LayoutInflater

    init {
        inflater = LayoutInflater.from(fActivity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val itemView = inflater.inflate(R.layout.news_row,parent,false)
        return FeedViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.newsTitle.text = rssObject.items[position].title
        holder.newsDate.text = rssObject.items[position].pubDate
        Picasso.get().load(rssObject.items[position].thumbnail).into(holder.newsImage)


        holder.setItemClickListener(ItemClickListener{ view, position, isLongClick ->

            if(!isLongClick)
            {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                fActivity?.startActivity(browserIntent)
            }
        } )
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

}

