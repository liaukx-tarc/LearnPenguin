package bigboss.team.learnpenguin.ui.course.j

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bigboss.team.learnpenguin.Adapter.VPAdapterJ

import bigboss.team.learnpenguin.databinding.FragmentCourseJBinding
import com.google.android.material.tabs.TabLayoutMediator

class CourseJFragment : Fragment() {
    private lateinit var binding: FragmentCourseJBinding

    var tabTitle = arrayOf("Overview",  "Contents", "FAQ")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCourseJBinding.inflate(inflater, container, false)
        val root = binding.root

        var pager = binding.viewPager2
        var tl = binding.tabLayout
        pager.adapter = VPAdapterJ(childFragmentManager , lifecycle)

        TabLayoutMediator(tl, pager){
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()

        return root
    }
}