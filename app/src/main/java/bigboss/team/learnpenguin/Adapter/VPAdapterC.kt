package bigboss.team.learnpenguin.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import bigboss.team.learnpenguin.ui.course.c.CourseContentC
import bigboss.team.learnpenguin.ui.course.c.CourseFaqC
import bigboss.team.learnpenguin.ui.course.c.CourseOverviewC

class VPAdapterC(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CourseOverviewC()
            1 -> return CourseContentC()
            2 -> return CourseFaqC()
            else -> return CourseOverviewC()
        }
    }
}