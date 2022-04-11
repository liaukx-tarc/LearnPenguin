package bigboss.team.learnpenguin.ui.collection

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import bigboss.team.learnpenguin.R
import bigboss.team.learnpenguin.databinding.FragmentCollectionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class FragmentCollection : Fragment() {

    private lateinit var binding: FragmentCollectionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var userDatabase: DatabaseReference
    private lateinit var courseDatabase: DatabaseReference
    private lateinit var storageRef: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userDatabase = FirebaseDatabase.getInstance().getReference("User")
        courseDatabase = FirebaseDatabase.getInstance().getReference("Course")
        storageRef = FirebaseStorage.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding = FragmentCollectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userDatabase.child("User").child(auth.uid.toString()).get().addOnSuccessListener { userResult->
            courseDatabase.get().addOnSuccessListener { courseResult->

                for (i in 0 until userResult.child("collection").childrenCount){

                    val courseID = userResult.child("collection").child(i.toString()).value.toString()

                    //Horizontal Linear Layout
                    var linearLayout = LinearLayout(activity)
                    var layoutParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(1030,380)
                    layoutParam.setMargins(20,40,20,0)
                    linearLayout.layoutParams = layoutParam
                    linearLayout.background = resources.getDrawable(R.drawable.collection_bg)
                    linearLayout.orientation = LinearLayout.HORIZONTAL

                    //Image
                    var imageView = ImageView(activity)
                    var imgParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(350, 350)
                    imageView.layoutParams = imgParam

                    val file = File.createTempFile("temp", "png")
                    val id = courseResult.child(courseID).child("language").value.toString()

                    storageRef.child("Image/${id}.png").getFile(file)
                        .addOnSuccessListener {
                            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                            imageView.setImageBitmap(bitmap)
                        }

                        .addOnFailureListener {ex->
                            imageView.setImageResource(R.drawable.penguin_logo)
                            toast(ex.message.toString())
                        }

                    linearLayout.addView(imageView)

                    //Detail Linear Layout
                    var detailLinearLayout = LinearLayout(activity)
                    var detailLinearParam = LinearLayout.LayoutParams(0, 0)
                    detailLinearParam.setMargins(60,20,0,0)
                    detailLinearLayout.orientation = LinearLayout.VERTICAL

                    //Text
                    var textView = TextView(activity)
                    var txtParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(500, 100)
                    txtParam.setMargins(0,20,0,50)
                    textView.layoutParams = txtParam
                    textView.textSize = 20F
                    textView.text = courseResult.child(courseID).child("name").value.toString()
                    textView.ellipsize = TextUtils.TruncateAt.MARQUEE
                    textView.maxLines = 1

                    detailLinearLayout.addView(textView)

                    //Star image button
                    var starImageView = ImageView(activity)
                    var starImgParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(75, 75)
                    starImageView.layoutParams = starImgParam
                    starImageView.setImageResource(R.drawable.star)

                    detailLinearLayout.addView(starImageView)

                    linearLayout.addView(detailLinearLayout)

                    //line
                    var lineImageView = ImageView(activity)
                    var lineImgParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(1030, 3)
                    lineImgParam.setMargins(20,40,20,0)
                    lineImageView.layoutParams = lineImgParam
                    lineImageView.setImageResource(R.drawable.line)

                    binding.linearCollection.addView(linearLayout)
                    binding.linearCollection.addView(lineImageView)
                }
            }

                .addOnFailureListener { ex->
                    toast(ex.message.toString())
                }
        }

            .addOnFailureListener { ex->
                toast(ex.message.toString())
            }



        return root
    }

    private fun toast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
}