package bigboss.team.learnpenguin.ui.profile

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import bigboss.team.learnpenguin.LoginActivity
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        database = FirebaseDatabase.getInstance().getReference("User")
        auth = FirebaseAuth.getInstance()

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database.child("User").child(auth.uid.toString()).get().addOnSuccessListener { result->
            //username
            viewModel.usersame = result.child("username").value.toString()
            binding.txtUsername.text = viewModel.usersame

            //role
            viewModel.role = result.child("role").value.toString()
            binding.txtRole.text = viewModel.role

            for(i in 0..result.child("collection").childrenCount - 1)
            {
                viewModel.collection.add(result.child("collection").child(i.toString()).value.toString())

                //Vectical Linear Layout
                var linearLayout = LinearLayout(activity)
                var layoutParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(350, 450)
                layoutParam.setMargins(20,30,0,20)
                linearLayout.layoutParams = layoutParam
                linearLayout.orientation = LinearLayout.VERTICAL

                //Image
                var imageView = ImageView(activity)
                var imgParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(350, 350)
                imageView.layoutParams = imgParam
                imageView.setImageResource(R.drawable.penguin_logo)

                linearLayout.addView(imageView)

                //Text
                var textView = TextView(activity)
                var txtParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(350, 100)
                txtParam.setMargins(0,15,0,0)
                textView.layoutParams = txtParam
                textView.textSize = 16F
                textView.text = viewModel.collection[i.toInt()]
                textView.ellipsize = TextUtils.TruncateAt.END
                textView.maxLines = 1

                linearLayout.addView(textView)

                binding.linearCollection.addView(linearLayout)
            }
        }
            .addOnFailureListener {
                toast("Fail to get data")
            }

        binding.imgbtnEdit.setOnClickListener{
            binding.txtUsername.visibility = View.INVISIBLE

        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            activity?.finishAffinity()

            toast("Account successful log out")
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun toast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

}