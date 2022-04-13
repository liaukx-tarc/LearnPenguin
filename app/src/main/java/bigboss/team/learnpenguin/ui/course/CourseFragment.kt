package bigboss.team.learnpenguin.ui.course

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import bigboss.team.learnpenguin.Adapter.CourseAdapter
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentCourseBinding
import bigboss.team.learnpenguin.ui.course.CourseMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var courseNameDatabase: DatabaseReference
    private lateinit var storageRef: StorageReference
    var newArrayList = ArrayList<CourseMenu>()

    private val fragmentList = arrayOf(R.id.courseCFragment, R.id.courseCsFragment, R.id.courseCppFragment, R.id.courseJFragment, R.id.courseJsFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        courseNameDatabase = FirebaseDatabase.getInstance().getReference("CourseName")
        storageRef = FirebaseStorage.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding = FragmentCourseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        courseNameDatabase.get().addOnSuccessListener { courseNameResult ->

            for (i in 0 until 5) {

                val file = File.createTempFile("temp", "png")
                val id = courseNameResult.child(i.toString()).value.toString()

                Log.i("Activity", id)

                storageRef.child("Image/${id}.png").getFile(file)
                    .addOnSuccessListener {
                        Log.i("Activity", "Successful")
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        val courseMenu = CourseMenu(bitmap, id)

                        newArrayList.add(courseMenu)

                        if(i == 4)
                        {
                            Log.i("Activity", "Binding")

                            binding.courseList.layoutManager = LinearLayoutManager(activity)
                            binding.courseList.setHasFixedSize(true)

                            binding.courseList.adapter = CourseAdapter(newArrayList, fragmentList)
                        }
                    }
            }
        }

        //newRecyclerView.adapter = CourseAdapter(newArrayList)

        return root
}
    }