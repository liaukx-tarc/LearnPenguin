package bigboss.team.learnpenguin.ui.course.c

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bigboss.team.learnpenguin.Adapter.VPAdapterC
import bigboss.team.learnpenguin.databinding.FragmentCourseCBinding
import com.google.android.material.tabs.TabLayoutMediator

class CourseCFragment : Fragment() {
    private lateinit var binding: FragmentCourseCBinding

    var tabTitle = arrayOf("Overview",  "Contents", "FAQ")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCourseCBinding.inflate(inflater, container, false)
        val root = binding.root

        var pager = binding.viewPager2
        var tl = binding.tabLayout
        pager.adapter = VPAdapterC(childFragmentManager , lifecycle)

        TabLayoutMediator(tl, pager){
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()

        return root
    }
}