package bigboss.team.learnpenguin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.ui.quiz.QuizMenu
import com.google.android.material.imageview.ShapeableImageView

class QuizAdapter (private val quizList : ArrayList<QuizMenu>) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    private lateinit var  itemListener: ItemClickListener

    interface ItemClickListener {

        fun onItemClick(position: Int)

    }

    fun setOnClickListener(listener: ItemClickListener){

        itemListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {

        val quizView = LayoutInflater.from(parent.context).inflate(R.layout.quiz_list, parent, false)

        return QuizViewHolder(quizView, itemListener)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {

        val currentItem = quizList[position]
        holder.quizImage.setImageBitmap(currentItem.image)
        holder.quizHeading.text = currentItem.heading
    }

    override fun getItemCount(): Int {

        return quizList.size
    }

    class QuizViewHolder(quizView: View, listener: ItemClickListener) : RecyclerView.ViewHolder(quizView){

        val quizImage : ShapeableImageView = quizView.findViewById(R.id.quizImage)
        val quizHeading : TextView = quizView.findViewById(R.id.quizHeading)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(adapterPosition)

             }
        }
    }
}