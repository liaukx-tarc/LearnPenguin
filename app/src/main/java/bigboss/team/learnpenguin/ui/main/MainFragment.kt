package bigboss.team.learnpenguin.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import bigboss.team.learnpenguin.Adapter.FeedAdapter
import bigboss.team.learnpenguin.MainActivity
import bigboss.team.learnpenguin.Model.NewsViewModel
import bigboss.team.learnpenguin.Model.RssObject
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentFavNewsBinding
import bigboss.team.learnpenguin.databinding.FragmentMainBinding
import bigboss.team.learnpenguin.databinding.FragmentNewsBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val model : NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        val recyclerView = binding.newsListMain
        recyclerView.layoutManager = linearLayoutManager

        model.isLoad.observe(viewLifecycleOwner, Observer {
            loadNews()
        })

        binding.seeAllNews.setOnClickListener { view ->
            Navigation.findNavController(view).navigate(R.id.navigation_news)
        }

        return root
    }

    private fun loadNews(){
        val rssObjectMain : RssObject = (activity as MainActivity?)!!.getRssObjectMain()
        if(activity != null)
        {
            val adapter = FeedAdapter(rssObjectMain,activity)
            val recyclerView = binding.newsListMain
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }
}