package bigboss.team.learnpenguin.ui.quiz

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import bigboss.team.learnpenguin.Adapter.QuizAdapter
import bigboss.team.learnpenguin.databinding.FragmentQuizBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var courseNameDatabase: DatabaseReference
    private lateinit var storageRef: StorageReference
    var newArrayList = ArrayList<QuizMenu>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        courseNameDatabase = FirebaseDatabase.getInstance().getReference("CourseName")
        storageRef = FirebaseStorage.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding = FragmentQuizBinding.inflate(inflater, container, false)
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
                        val quizMenu = QuizMenu(bitmap, id)

                        newArrayList.add(quizMenu)

                        if(i == 4)
                        {
                            Log.i("Activity", "Binding")

                            binding.quizList.layoutManager = LinearLayoutManager(activity)
                            binding.quizList.setHasFixedSize(true)

                            binding.quizList.adapter = QuizAdapter(newArrayList)
                        }
                    }
            }

            var adapter = QuizAdapter(newArrayList)
            adapter.setOnClickListener(object : QuizAdapter.ItemClickListener{

               override fun onItemClick(position: Int) {

                    Toast.makeText(activity, "Clicked on No.$position", Toast.LENGTH_SHORT).show()

                }

            })
        }

            //newRecyclerView.adapter = QuizAdapter(newArrayList)

        return root
    }

}