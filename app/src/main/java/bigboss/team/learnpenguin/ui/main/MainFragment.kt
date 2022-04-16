package bigboss.team.learnpenguin.ui.main

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import bigboss.team.learnpenguin.Adapter.CourseAdapter
import bigboss.team.learnpenguin.Adapter.FeedAdapter
import bigboss.team.learnpenguin.MainActivity
import bigboss.team.learnpenguin.Model.NewsViewModel
import bigboss.team.learnpenguin.Model.RssObject
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentMainBinding
import bigboss.team.learnpenguin.ui.course.CourseMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val model : NewsViewModel by activityViewModels()

    //Course
    private lateinit var auth: FirebaseAuth
    private lateinit var courseNameDatabase: DatabaseReference
    private lateinit var storageRef: StorageReference
    var newArrayList = ArrayList<CourseMenu>()
    var isCreated = false
    private val newFragmentList = ArrayList<Int>()

    private val fragmentList = arrayOf(
        R.id.courseCFragment,
        R.id.courseCppFragment,
        R.id.courseCsFragment,
        R.id.courseJFragment,
        R.id.courseJsFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        courseNameDatabase = FirebaseDatabase.getInstance().getReference("CourseName")
        storageRef = FirebaseStorage.getInstance().reference
        auth = FirebaseAuth.getInstance()
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        val recyclerView = binding.newsListMain
        recyclerView.layoutManager = linearLayoutManager

        model.isLoad.observe(viewLifecycleOwner, Observer {
            loadNews()
        })

        binding.courseListMain.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        loadCourse()

        binding.seeAllNews.setOnClickListener { view ->
            Navigation.findNavController(view).navigate(R.id.navigation_news)
        }

        binding.seeAllCourse.setOnClickListener{ view ->
            Navigation.findNavController(view).navigate(R.id.courseFragment)
        }

        return root
    }

    private fun loadCourse() {
        if (!isCreated) {
            courseNameDatabase.get().addOnSuccessListener { courseNameResult ->

                var successfulCount = 0

                for (i in 0 until 3) {
                    val file = File.createTempFile("temp", "png")
                    val id = courseNameResult.child(i.toString()).value.toString()

                    var courseMenu = CourseMenu(BitmapFactory.decodeResource(context?.resources, R.drawable.penguin_logo), "")

                    storageRef.child("Image/${id}.png").getFile(file)
                        .addOnSuccessListener {

                            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                            courseMenu = CourseMenu(bitmap, id)
                        }

                        .addOnCompleteListener{
                            newArrayList.add(courseMenu)
                            newFragmentList.add(fragmentList[i])
                            successfulCount++

                            if (successfulCount == 3) {
                                binding.courseListMain.setHasFixedSize(true)

                                binding.courseListMain.adapter = CourseAdapter(newArrayList, newFragmentList.toTypedArray())

                                isCreated = true
                            }
                        }
                }

            }

        } else {
            binding.courseListMain.setHasFixedSize(true)

            binding.courseListMain.adapter = CourseAdapter(newArrayList, newFragmentList.toTypedArray())
        }
    }


    private fun loadNews(){
        val rssObjectMain : RssObject = (activity as MainActivity?)!!.getRssObjectMain()
        if(activity != null)
        {
            val adapter = FeedAdapter(rssObjectMain,activity)
            val recyclerView = binding.newsListMain
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()

        }
    }
}