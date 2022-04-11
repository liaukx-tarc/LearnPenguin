package bigboss.team.learnpenguin.ui.news

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigboss.team.learnpenguin.Adapter.FeedAdapter
import bigboss.team.learnpenguin.Common.HTTPDataHandler
import bigboss.team.learnpenguin.Model.RssObject
import bigboss.team.learnpenguin.databinding.FragmentNewsBinding
import com.google.gson.Gson
import java.lang.StringBuilder


class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val rssLink = "https://www.howtogeek.com/feed/"
    private val rss2JsonApi = "https://api.rss2json.com/v1/api.json?rss_url="

    val newsList : RecyclerView ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        val recyclerView = binding.newsList
        recyclerView.layoutManager = linearLayoutManager

        loadRSS()

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
        urlGetData.append(rssLink)
        loadRssASync.execute(urlGetData.toString())
    }


}