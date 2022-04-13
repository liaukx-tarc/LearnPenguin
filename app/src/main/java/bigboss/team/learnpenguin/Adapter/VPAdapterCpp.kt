package bigboss.team.learnpenguin.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import bigboss.team.learnpenguin.ui.course.cpp.CourseContentCpp
import bigboss.team.learnpenguin.ui.course.cpp.CourseFaqCpp
import bigboss.team.learnpenguin.ui.course.cpp.CourseOverviewCpp

class VPAdapterCpp(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CourseOverviewCpp()
            1 -> return CourseContentCpp()
            2 -> return CourseFaqCpp()
            else -> return CourseOverviewCpp()
        }
    }
}