package bigboss.team.learnpenguin.ui.news

import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import bigboss.team.learnpenguin.Adapter.FeedAdapter
import bigboss.team.learnpenguin.Common.HTTPDataHandler
import bigboss.team.learnpenguin.Model.RssObject
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentNewsBinding
import com.google.gson.Gson


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
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

        if(activity != null && rssLink!= null)
        {
            loadRSS()
        }
        swipeRefreshLayout = binding.newsSwipe
        swipeRefreshLayout.setOnRefreshListener {
            if(activity != null)
            {
                loadRSS()
            }
            recyclerView.adapter?.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }

        binding.favNews.setOnClickListener { view ->
            Navigation.findNavController(view).navigate(R.id.navigation_fav_news)
        }
        return root
    }

    private fun loadRSS() {
        val loadRssASync = object:AsyncTask<String,String,String>(){
            override fun doInBackground(vararg params: String?): String {
                val result:String
                val http = HTTPDataHandler()
                result = http.GetHTTPDataHandler(params[0])
                return result
            }

            override fun onPostExecute(result: String?) {
                var rssObject:RssObject
                rssObject = Gson().fromJson<RssObject>(result,RssObject::class.java!!)
                val adapter = FeedAdapter(rssObject,activity)
                val recyclerView = binding.newsList
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }



        }

        val urlGetData = StringBuilder(rss2JsonApi)
        urlGetData.append(rssLink+"&api_key=yjgpynj8b3683nl4mvgynzpdcikfzu9pyodlgg8a&count=25")
        loadRssASync.execute(urlGetData.toString())
    }


}