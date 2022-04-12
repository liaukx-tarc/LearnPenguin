package bigboss.team.learnpenguin.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bigboss.team.learnpenguin.databinding.FragmentFavNewsBinding
import bigboss.team.learnpenguin.databinding.FragmentNewsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavNewsFragment : Fragment() {

    private lateinit var binding: FragmentFavNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavNewsBinding.inflate(inflater, container, false)

        val root: View = binding.root
        return root
    }


}