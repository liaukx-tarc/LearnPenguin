package bigboss.team.learnpenguin.ui.profile

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentCollectionBinding
import bigboss.team.learnpenguin.databinding.FragmentRenameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

class FragmentRename : Fragment() {

    private lateinit var binding: FragmentRenameBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        database = FirebaseDatabase.getInstance().getReference("User")
        auth = FirebaseAuth.getInstance()

        binding = FragmentRenameBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnRename.setOnClickListener { view->
            binding.errorUsername.text = null
            val username = binding.inputUsername.text.toString()

            if(validation(username)){
                database.child("User").child(auth.uid.toString()).child("username").setValue(username).addOnSuccessListener {
                    toast("Rename successful")
                    Navigation.findNavController(view).navigate(R.id.navigation_profile)
                }
                    .addOnFailureListener { ex->
                        toast(ex.message.toString())
                    }
            }
        }

        return root
    }

    private fun toast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    private fun validation(username: String): Boolean {

        var isCorrect = true

        //username validation
        if (username.isBlank()) {
            binding.errorUsername.text = getString(R.string.username_blank)
            isCorrect = false
        } else if (username.length > 50) {
            binding.errorUsername.text = getString(R.string.username_More50)
            isCorrect = false
        }

        return isCorrect
    }
}