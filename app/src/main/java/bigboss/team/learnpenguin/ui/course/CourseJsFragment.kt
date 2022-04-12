package bigboss.team.learnpenguin.ui.course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bigboss.team.learnpenguin.databinding.FragmentCourseJsBinding

class CourseJsFragment : Fragment() {
    private lateinit var binding: FragmentCourseJsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseJsBinding.inflate(inflater, container, false)
        val root = binding.root

        return root

    }
}