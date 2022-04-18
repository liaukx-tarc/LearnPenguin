package bigboss.team.learnpenguin.ui.quiz

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.AdaptiveIconDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import bigboss.team.learnpenguin.Adapter.QuizAdapter
import bigboss.team.learnpenguin.Adapter.QuizViewHolder
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentNewsBinding
import bigboss.team.learnpenguin.databinding.FragmentQuizBinding
import bigboss.team.learnpenguin.databinding.FragmentQuizQuestionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import org.w3c.dom.Text
import android.util.Log.i as i1

class QuizQuestionFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentQuizQuestionBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var quizDatabase: DatabaseReference

    private var currentPosition: Int = 1
    private var quizQuestionList = ArrayList<QuizQuestion>()
    private var selectedOption: Int = 0
    private var correctCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var quizHeading = requireArguments().getString("Heading")
        //Log.i("Quiz", quizHeading.toString())

        if (quizHeading.toString() == "C++"){
            quizHeading = "C Plus"
        }

        if (quizHeading.toString() == "C#"){
            quizHeading = "C Sharp"
        }

        quizDatabase = FirebaseDatabase.getInstance().getReference("Quiz")
        auth = FirebaseAuth.getInstance()

        binding = FragmentQuizQuestionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        quizDatabase.get().addOnSuccessListener { quizResult ->

            for (i in 1 until 11){

                val getId = quizResult.child(quizHeading.toString()).child(i.toString()).child("id").value.toString().toInt()
                val getQuestion = quizResult.child(quizHeading.toString()).child(i.toString()).child("question").value.toString()
                val getOptionOne = quizResult.child(quizHeading.toString()).child(i.toString()).child("optionOne").value.toString()
                val getOptionTwo = quizResult.child(quizHeading.toString()).child(i.toString()).child("optionTwo").value.toString()
                val getOptionThree = quizResult.child(quizHeading.toString()).child(i.toString()).child("optionThree").value.toString()
                val getOptionFour = quizResult.child(quizHeading.toString()).child(i.toString()).child("optionFour").value.toString()
                val getAnswer = quizResult.child(quizHeading.toString()).child(i.toString()).child("answer").value.toString().toInt()

                val getQuestionList = QuizQuestion(getId, getQuestion, getOptionOne, getOptionTwo, getOptionThree, getOptionFour, getAnswer)
                quizQuestionList.add(getQuestionList)

            }

            Log.i("Firebase", "Question Size ${quizQuestionList.size}")

        }

            .addOnFailureListener { ex ->
                toast(ex.message.toString())
                //Log.i("Firebase", "Failed")
            }

            .addOnCompleteListener {
                setQuestion()
            }

        //i1("Activity", "Question List Size ${quizQuestionList!!.size}")

        binding.quizOptionOne.setOnClickListener(this)
        binding.quizOptionTwo.setOnClickListener(this)
        binding.quizOptionThree.setOnClickListener(this)
        binding.quizOptionFour.setOnClickListener(this)
        binding.quizSubmitBtn.setOnClickListener(this)

        // Inflate the layout for this fragment

        return root
    }

    override fun onClick(quizQuestionView: View?){
        if (quizQuestionView != null) {
            when (quizQuestionView.id){

                binding.quizOptionOne.id -> {
                    selectedOptionView(binding.quizOptionOne, 1)
                }

                binding.quizOptionTwo.id -> {
                    selectedOptionView(binding.quizOptionTwo, 2)
                }

                binding.quizOptionThree.id -> {
                    selectedOptionView(binding.quizOptionThree, 3)
                }

                binding.quizOptionFour.id -> {
                    selectedOptionView(binding.quizOptionFour, 4)
                }

                binding.quizSubmitBtn.id -> {
                    if (selectedOption == 0) {
                        when {
                            currentPosition <= quizQuestionList.size -> {
                                setQuestion()
                            }
                            else -> {
                                Toast.makeText(activity, "You Completed Quiz", Toast.LENGTH_SHORT).show()
                                Log.i("Correct Count", "$correctCount")

                                //Navigation.findNavController(view).navigate(R.id.navigation_quiz)
                            }
                        }
                    } else {
                        val question = quizQuestionList.get(currentPosition - 1)
                        if(question.answer != selectedOption){
                            answerView(selectedOption, R.drawable.wrong_option_border_bg)
                        }
                        else{
                            correctCount++
                        }

                        answerView(question.answer, R.drawable.correct_option_border_bg)


                        if(currentPosition == quizQuestionList.size){
                            binding.quizSubmitBtn.text = "Finish"
                        } else {
                            binding.quizSubmitBtn.text = "Next Question"
                            currentPosition++
                        }

                        selectedOption = 0
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setQuestion() {

        val question = quizQuestionList.get(currentPosition - 1)

        defaultOptionView()

        if (currentPosition == quizQuestionList.size){
            binding.quizSubmitBtn.text = "Finish"
        }
        else{
            binding.quizSubmitBtn.text = "Submit"
        }

        binding.questionProgressBar.progress = currentPosition
        binding.questionProgressCount.text = "$currentPosition" + "/" + "${binding.questionProgressBar.max}"

        binding.quizQuestion.text = question.question
        binding.quizOptionOne.text = question.optionOne
        binding.quizOptionTwo.text = question.optionTwo
        binding.quizOptionThree.text = question.optionThree
        binding.quizOptionFour.text = question.optionFour

    }

    private fun defaultOptionView() {

        val options = ArrayList<TextView>()
        options.add(0, binding.quizOptionOne)
        options.add(1, binding.quizOptionTwo)
        options.add(2, binding.quizOptionThree)
        options.add(3, binding.quizOptionFour)

        for (option in options){

            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(activity!!, R.drawable.default_option_border_bg)

        }

    }

    private fun selectedOptionView(quizOption: TextView, selectedOptionNum: Int){

        defaultOptionView()
        selectedOption = selectedOptionNum
        quizOption.setTextColor(Color.parseColor("#363A43"))
        quizOption.setTypeface(quizOption.typeface, Typeface.BOLD)
        quizOption.background = ContextCompat.getDrawable(activity!!, R.drawable.selected_option_border_bg)

    }

    private fun answerView(answer: Int, drawableView: Int){

        when (answer){

            1 -> {
                binding.quizOptionOne.background = ContextCompat.getDrawable(activity!!, drawableView)
            }

            2 -> {
                binding.quizOptionTwo.background = ContextCompat.getDrawable(activity!!, drawableView)
            }

            3 -> {
                binding.quizOptionThree.background = ContextCompat.getDrawable(activity!!, drawableView)
            }

            4 -> {
                binding.quizOptionFour.background = ContextCompat.getDrawable(activity!!, drawableView)
            }
        }
    }

    private fun toast(text: String){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
}


