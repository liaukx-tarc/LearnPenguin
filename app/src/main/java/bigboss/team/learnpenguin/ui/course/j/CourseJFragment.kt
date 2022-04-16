package bigboss.team.learnpenguin.ui.course.j

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import bigboss.team.learnpenguin.Adapter.VPAdapterJ
import bigboss.team.learnpenguin.R

import bigboss.team.learnpenguin.databinding.FragmentCourseJBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CourseJFragment : Fragment() {
    private lateinit var binding: FragmentCourseJBinding

    var tabTitle = arrayOf("Overview",  "Contents", "FAQ")

    private lateinit var auth: FirebaseAuth
    private lateinit var userDatabase: DatabaseReference
    var isFavorite: Boolean = false
    lateinit var item: MenuItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCourseJBinding.inflate(inflater, container, false)
        val root = binding.root

        var pager = binding.viewPager2
        var tl = binding.tabLayout
        pager.adapter = VPAdapterJ(childFragmentManager , lifecycle)

        TabLayoutMediator(tl, pager){
            tab, position ->
                tab.text = tabTitle[position]
        }.attach()

        //Favorite button
        userDatabase = FirebaseDatabase.getInstance().getReference("User")
        auth = FirebaseAuth.getInstance()

        userDatabase.child("User").child(auth.uid.toString()).child("collection").child("3").get()
            .addOnSuccessListener { collectionResult->
                isFavorite = collectionResult.value.toString().toBoolean()
                if(isFavorite)
                {
                    item.setIcon(R.drawable.star)
                }
            }

        return root
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.course_action_menu, menu)
        item = menu.findItem(R.id.action_favourite)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favourite -> {
                if(isFavorite)
                    userDatabase.child("User").child(auth.uid.toString()).child("collection").child("3").setValue(false)
                        .addOnSuccessListener {
                            item.setIcon(R.drawable.star_outline)
                            isFavorite = false
                            Toast.makeText(activity, "Favourite course removed", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener{
                            Toast.makeText(activity, "Fail to remove", Toast.LENGTH_SHORT).show()
                        }

                else{
                    userDatabase.child("User").child(auth.uid.toString()).child("collection").child("3").setValue(true)
                        .addOnSuccessListener {
                            item.setIcon(R.drawable.star)
                            isFavorite = true
                            Toast.makeText(activity, "Favourite course added", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener{
                            Toast.makeText(activity, "Fail to add", Toast.LENGTH_SHORT).show()
                        }
                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}