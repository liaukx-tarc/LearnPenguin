package bigboss.team.learnpenguin.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import bigboss.team.learnpenguin.ui.course.cs.CourseContentCs
import bigboss.team.learnpenguin.ui.course.cs.CourseFaqCs
import bigboss.team.learnpenguin.ui.course.cs.CourseOverviewCs

class VPAdapterCs(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CourseOverviewCs()
            1 -> return CourseContentCs()
            2 -> return CourseFaqCs()
            else -> return CourseOverviewCs()
        }
    }
}