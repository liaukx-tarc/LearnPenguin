package bigboss.team.learnpenguin.ui.quiz

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentQuizQuestionBinding
import bigboss.team.learnpenguin.databinding.FragmentQuizResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class QuizResultFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentQuizResultBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var userDatabase: DatabaseReference

    //private var totalIncrement: Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var correctCount = requireArguments().getInt("Count")

        /*
        var quizTitle = requireArguments().getString("Course")

        if (quizTitle.toString() == "C Plus"){
            quizTitle = "C++"
        }

        if (quizTitle.toString() == "C Sharp"){
            quizTitle = "C#"
        }

        totalIncrement = correctCount*10

         */

        userDatabase = FirebaseDatabase.getInstance().getReference("User")
        auth = FirebaseAuth.getInstance()

        binding = FragmentQuizResultBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.circularProgressBar.apply {
            progress = correctCount.toFloat()
            setProgressWithAnimation(progress, 1000) // =1s
            progressMax = 10f
        }

        binding.counter.text = "$correctCount/10"

        userDatabase.child("User").child(auth.uid.toString()).get().addOnSuccessListener { userResult ->

            var username = userResult.child("username").value.toString()

            if (correctCount != 0) {
                binding.resultText.text = "Congratulations! $username. You've completed the quiz."
            }
            else{
                binding.resultText.text = "Good Try, $username."
            }

        }

        // Inflate the layout for this fragment

        binding.returnBtn.setOnClickListener {
            view?.let { it -> Navigation.findNavController(it).navigate(R.id.navigation_quiz) }
        }

        return root
    }

    override fun onClick(view: View?) {
        TODO("Not yet implemented")
    }

}