package bigboss.team.learnpenguin.ui.profile

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import bigboss.team.learnpenguin.LoginActivity
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File


class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var userDatabase: DatabaseReference
    private lateinit var courseDatabase: DatabaseReference
    private lateinit var courseNameDatabase: DatabaseReference
    private lateinit var storageRef: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        userDatabase = FirebaseDatabase.getInstance().getReference("User")
        courseDatabase = FirebaseDatabase.getInstance().getReference("Course")
        courseNameDatabase = FirebaseDatabase.getInstance().getReference("CourseName")
        storageRef = FirebaseStorage.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.fragmentContainerView.visibility = View.INVISIBLE

        userDatabase.child("User").child(auth.uid.toString()).get().addOnSuccessListener { userResult ->
            courseNameDatabase.get().addOnSuccessListener { courseNameResult ->
                courseDatabase.get().addOnSuccessListener { courseResult ->
                    //username
                    viewModel.username = userResult.child("username").value.toString()
                    binding.txtUsername.text = viewModel.username

                    //role
                    viewModel.role = userResult.child("role").value.toString()
                    binding.txtRole.text = viewModel.role

                    for (i in 0 until userResult.child("collection").childrenCount) {

                        if(userResult.child("collection").child(i.toString()).value == true)
                        {
                            val courseID = courseNameResult.child(i.toString()).value.toString()
                            viewModel.collection.add(courseID)

                            //Vectical Linear Layout
                            var linearLayout = LinearLayout(activity)
                            var layoutParam: LinearLayout.LayoutParams =
                                LinearLayout.LayoutParams(350, 450)
                            layoutParam.setMargins(20, 30, 0, 30)
                            linearLayout.layoutParams = layoutParam
                            linearLayout.orientation = LinearLayout.VERTICAL
                            linearLayout.setBackgroundColor(resources.getColor(R.color.light_gray))
                            linearLayout.isClickable = true
                            linearLayout.setOnClickListener {
                                Navigation.findNavController(it).navigate(
                                    resources.getIdentifier(
                                        courseResult.child(courseID).child("id").value.toString(),
                                        "id", activity?.packageName
                                    )
                                )
                            }

                            //Image
                            var imageView = ImageView(activity)
                            var imgParam: LinearLayout.LayoutParams =
                                LinearLayout.LayoutParams(350, 350)
                            imageView.layoutParams = imgParam

                            val file = File.createTempFile("temp", "png")

                            storageRef.child("Image/${courseID}.png").getFile(file)
                                .addOnSuccessListener {
                                    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                                    imageView.setImageBitmap(bitmap)
                                }

                                .addOnFailureListener { ex ->
                                    imageView.setImageResource(R.drawable.penguin_logo)
                                    toast(ex.message.toString())
                                }

                            linearLayout.addView(imageView)

                            //Text
                            var textView = TextView(activity)
                            var txtParam: LinearLayout.LayoutParams =
                                LinearLayout.LayoutParams(350, 100)
                            txtParam.setMargins(0, 15, 0, 0)
                            textView.layoutParams = txtParam
                            textView.textSize = 16F
                            textView.text = courseResult.child(courseID).child("name").value.toString()
                            textView.ellipsize = TextUtils.TruncateAt.MARQUEE
                            textView.maxLines = 1

                            linearLayout.addView(textView)

                            binding.linearCollection.addView(linearLayout)
                        }
                    }
                }
                    .addOnFailureListener { ex ->
                        toast(ex.message.toString())
                    }
            }
                .addOnFailureListener { ex ->
                    toast(ex.message.toString())
                }
        }
            .addOnFailureListener { ex ->
                toast(ex.message.toString())
            }

        binding.imgbtnEdit.setOnClickListener{
            binding.fragmentContainerView.visibility = View.VISIBLE
            Navigation.findNavController(it).navigate(R.id.navigation_rename)
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            activity?.finishAffinity()

            toast("Account successful log out")
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.txtbtnSeeAll.setOnClickListener {
            binding.fragmentContainerView.visibility = View.VISIBLE
            Navigation.findNavController(it).navigate(R.id.navigation_collection)
        }

        return root
    }

    private fun toast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

}