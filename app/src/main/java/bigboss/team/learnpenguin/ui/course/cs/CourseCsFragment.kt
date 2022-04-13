package bigboss.team.learnpenguin.ui.course.cs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bigboss.team.learnpenguin.Adapter.VPAdapterCs

import bigboss.team.learnpenguin.databinding.FragmentCourseCsBinding
import com.google.android.material.tabs.TabLayoutMediator

class CourseCsFragment : Fragment() {
    private lateinit var binding: FragmentCourseCsBinding

    var tabTitle = arrayOf("Overview",  "Contents", "FAQ")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCourseCsBinding.inflate(inflater, container, false)
        val root = binding.root

        var pager = binding.viewPager2
        var tl = binding.tabLayout
        pager.adapter = VPAdapterCs(childFragmentManager , lifecycle)

        TabLayoutMediator(tl, pager){
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()

        return root
    }
}