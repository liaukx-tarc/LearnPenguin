package bigboss.team.learnpenguin.ui.course.cpp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bigboss.team.learnpenguin.Adapter.VPAdapterCpp
import bigboss.team.learnpenguin.databinding.FragmentCourseCppBinding
import com.google.android.material.tabs.TabLayoutMediator

class CourseCppFragment : Fragment() {
    private lateinit var binding: FragmentCourseCppBinding

    var tabTitle = arrayOf("Overview",  "Contents", "FAQ")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCourseCppBinding.inflate(inflater, container, false)
        val root = binding.root

        var pager = binding.viewPager2
        var tl = binding.tabLayout
        pager.adapter = VPAdapterCpp(childFragmentManager , lifecycle)

        TabLayoutMediator(tl, pager){
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()

        return root
    }
}