package bigboss.team.learnpenguin.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import bigboss.team.learnpenguin.ui.course.j.CourseContentJ
import bigboss.team.learnpenguin.ui.course.j.CourseFaqJ
import bigboss.team.learnpenguin.ui.course.j.CourseOverviewJ

class VPAdapterJ(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CourseOverviewJ()
            1 -> return CourseContentJ()
            2 -> return CourseFaqJ()
            else -> return CourseOverviewJ()
        }
    }
}