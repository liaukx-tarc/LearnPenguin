package bigboss.team.learnpenguin.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import bigboss.team.learnpenguin.ui.course.js.CourseContentJs
import bigboss.team.learnpenguin.ui.course.js.CourseFaqJs
import bigboss.team.learnpenguin.ui.course.js.CourseOverviewJs

class VPAdapterJs(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return CourseOverviewJs ()
            1 -> return CourseContentJs ()
            2 -> return CourseFaqJs ()
            else -> return CourseOverviewJs ()
        }
    }
}