package bigboss.team.learnpenguin.Adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import bigboss.team.learnpenguin.Interface.ItemClickListener
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.ui.course.CourseMenu
import com.google.android.material.imageview.ShapeableImageView

class CourseViewHolder(courseView: View) : RecyclerView.ViewHolder(courseView), View.OnClickListener,View.OnLongClickListener {

    val courseImage: ShapeableImageView = courseView.findViewById(R.id.courseImage)
    val courseHeading: TextView = courseView.findViewById(R.id.courseHeading)

    private var itemListener: ItemClickListener ?= null

    init {
        courseView.setOnClickListener(this)
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

class CourseAdapter (private val courseList : ArrayList<CourseMenu>, private val fragmentList: Array<Int>) : RecyclerView.Adapter<CourseViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {

        val courseView =
            LayoutInflater.from(parent.context).inflate(R.layout.course_list, parent, false)

        return CourseViewHolder(courseView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {

        val currentItem = courseList[position]
        holder.courseImage.setImageBitmap(currentItem.image)
        holder.courseHeading.text = currentItem.heading

        holder.setItemClickListener(ItemClickListener{ view, position, isLongClick ->

            if(!isLongClick)
            {
                Navigation.findNavController(view).navigate(fragmentList[position])
            }
        } )
    }

    override fun getItemCount(): Int {

        return courseList.size
    }
}


