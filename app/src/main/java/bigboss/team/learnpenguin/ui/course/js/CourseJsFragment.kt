package bigboss.team.learnpenguin.ui.course.js

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bigboss.team.learnpenguin.Adapter.VPAdapterJs
import bigboss.team.learnpenguin.databinding.FragmentCourseJsBinding
import com.google.android.material.tabs.TabLayoutMediator

class CourseJsFragment : Fragment() {
    private lateinit var binding: FragmentCourseJsBinding

    var tabTitle = arrayOf("Overview",  "Contents", "FAQ")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCourseJsBinding.inflate(inflater, container, false)
        val root = binding.root

        var pager = binding.viewPager2
        var tl = binding.tabLayout
        pager.adapter = VPAdapterJs(childFragmentManager , lifecycle)

        TabLayoutMediator(tl, pager){
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()

        return root
    }
}