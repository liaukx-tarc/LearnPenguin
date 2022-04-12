package bigboss.team.learnpenguin.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.ui.course.CourseMenu
import com.google.android.material.imageview.ShapeableImageView

class CourseAdapter (private val courseList : ArrayList<CourseMenu>) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    private lateinit var itemListener: ItemClickListener

    interface ItemClickListener {

        fun onItemClick(position: Int)

    }

    fun setOnClickListener(listener: ItemClickListener) {

        itemListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {

        val courseView =
            LayoutInflater.from(parent.context).inflate(R.layout.course_list, parent, false)

        return CourseViewHolder(courseView, itemListener)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {

        val currentItem = courseList[position]
        holder.courseImage.setImageBitmap(currentItem.image)
        holder.courseHeading.text = currentItem.heading
    }

    override fun getItemCount(): Int {

        return courseList.size
    }

    class CourseViewHolder(courseView: View, listener: ItemClickListener) :
        RecyclerView.ViewHolder(courseView) {

        val courseImage: ShapeableImageView = courseView.findViewById(R.id.courseImage)
        val courseHeading: TextView = courseView.findViewById(R.id.courseHeading)

        init {
            itemView.setOnClickListener {

                listener.onItemClick(adapterPosition)

            }
        }
    }
}
