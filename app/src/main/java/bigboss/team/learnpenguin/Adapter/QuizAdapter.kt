package bigboss.team.learnpenguin.Adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import bigboss.team.learnpenguin.Interface.ItemClickListener
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.ui.quiz.QuizMenu
import bigboss.team.learnpenguin.ui.quiz.QuizQuestionFragment
import com.google.android.material.imageview.ShapeableImageView
import android.content.Context.MODE_PRIVATE
import android.content.Intent

import android.content.SharedPreferences
import androidx.navigation.Navigation.findNavController


class QuizViewHolder(quizView: View) : RecyclerView.ViewHolder(quizView), View.OnClickListener,View.OnLongClickListener {

    val quizImage : ShapeableImageView = quizView.findViewById(R.id.quizImage)
    val quizHeading : TextView = quizView.findViewById(R.id.quizHeading)

    private var itemListener: ItemClickListener ?= null

    init {
        quizView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener)
    {
        this.itemListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemListener!!.onClick(v,absoluteAdapterPosition,false)
    }

    override fun onLongClick(v: View?): Boolean {
        itemListener!!.onClick(v,absoluteAdapterPosition,true)
        return true
    }
}

class QuizAdapter (private val quizList : ArrayList<QuizMenu>) : RecyclerView.Adapter<QuizViewHolder>() {

    private lateinit var itemListener: ItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {

        val quizView = LayoutInflater.from(parent.context).inflate(R.layout.quiz_list, parent, false)

        return QuizViewHolder(quizView)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {

        val currentItem = quizList[position]
        holder.quizImage.setImageBitmap(currentItem.image)
        holder.quizHeading.text = currentItem.heading

        holder.setItemClickListener(ItemClickListener{ view, position, isLongClick ->

            if(!isLongClick)
            {
                Log.i("Activity", "Click")
                findNavController(view).navigate(R.id.navigation_quiz_question, Bundle().apply {
                    putString("Heading", currentItem.heading)
                })

                //Log.i("Course", "Num: $position")

            }
        } )
    }

    override fun getItemCount(): Int {

        return quizList.size
    }

}


