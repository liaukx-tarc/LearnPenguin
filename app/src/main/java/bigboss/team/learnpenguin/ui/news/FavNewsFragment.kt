package bigboss.team.learnpenguin.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bigboss.team.learnpenguin.Adapter.FavNewsAdapter
import bigboss.team.learnpenguin.Common.SwipeToDeleteFunction
import bigboss.team.learnpenguin.MainActivity
import bigboss.team.learnpenguin.Model.FavNewsObject
import bigboss.team.learnpenguin.databinding.FragmentFavNewsBinding


class FavNewsFragment : Fragment() {

    private lateinit var binding: FragmentFavNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavNewsBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        linearLayoutManager.reverseLayout = true
        val recyclerView = binding.favNewsList
        recyclerView.layoutManager = linearLayoutManager

        if(activity != null)
        {
            loadFavNews()
        }

        val swipeFunction = object : SwipeToDeleteFunction(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction)
                {
                    ItemTouchHelper.LEFT ->{
                        (activity as MainActivity?)!!.removeFavNews(viewHolder.absoluteAdapterPosition)
                        recyclerView.adapter?.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeFunction)
        touchHelper.attachToRecyclerView(recyclerView)
        return root
    }

    private fun loadFavNews(){
        val favNewsObjectList : ArrayList<FavNewsObject> = (activity as MainActivity?)!!.getFavNewsObjectList()
        if(activity != null)
        {
            val adapter = FavNewsAdapter(favNewsObjectList,activity)
            val recyclerView = binding.favNewsList
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }


}