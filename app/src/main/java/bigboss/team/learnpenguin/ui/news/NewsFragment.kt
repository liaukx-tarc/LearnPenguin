package bigboss.team.learnpenguin.ui.news

import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import bigboss.team.learnpenguin.Adapter.FavNewsAdapter
import bigboss.team.learnpenguin.Adapter.FeedAdapter
import bigboss.team.learnpenguin.Common.HTTPDataHandler
import bigboss.team.learnpenguin.Common.SwipeToAddFunction
import bigboss.team.learnpenguin.MainActivity
import bigboss.team.learnpenguin.Model.FavNewsObject
import bigboss.team.learnpenguin.Model.RssObject
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentNewsBinding
import com.google.gson.Gson


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var rssObject: RssObject
// ...

    private val rssLink = "http://feeds.arstechnica.com/arstechnica/technology-lab"
    private val rss2JsonApi = "https://api.rss2json.com/v1/api.json?rss_url="

    private lateinit var swipeRefreshLayout:SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        val recyclerView = binding.newsList
        recyclerView.layoutManager = linearLayoutManager

        if(activity != null)
        {
            loadNews()
        }
        swipeRefreshLayout = binding.newsSwipe
        swipeRefreshLayout.setOnRefreshListener {
            if(activity != null)
            {
                (activity as MainActivity?)!!.loadRSS()
                loadNews()
            }
            swipeRefreshLayout.isRefreshing = false
        }

        binding.favNews.setOnClickListener { view ->
            Navigation.findNavController(view).navigate(R.id.navigation_fav_news)
        }

        val swipeFunction = object : SwipeToAddFunction(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction)
                {
                    ItemTouchHelper.RIGHT ->{
                        val rssObject : RssObject = (activity as MainActivity?)!!.getRssObject()
                        val favNewsObject = FavNewsObject(rssObject.items[viewHolder.absoluteAdapterPosition].title
                            ,rssObject.items[viewHolder.absoluteAdapterPosition].pubDate
                            ,rssObject.items[viewHolder.absoluteAdapterPosition].thumbnail
                            ,rssObject.items[viewHolder.absoluteAdapterPosition].link)
                        (activity as MainActivity?)!!.addFavNews(favNewsObject)
                        recyclerView.adapter?.notifyItemChanged(viewHolder.absoluteAdapterPosition)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeFunction)
        touchHelper.attachToRecyclerView(recyclerView)

        return root
    }

    private fun loadNews(){
        val rssObject : RssObject = (activity as MainActivity?)!!.getRssObject()
        if(activity != null)
        {
            val adapter = FeedAdapter(rssObject,activity)
            val recyclerView = binding.newsList
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

}