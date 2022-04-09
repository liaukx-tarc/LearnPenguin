package bigboss.team.learnpenguin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import bigboss.team.learnpenguin.databinding.ActivityForgetPassBinding
import bigboss.team.learnpenguin.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class ForgetPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPassBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.txtbtnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }

        binding.imgbtnBack.setOnClickListener {
            finish()
        }

        binding.btnSend.setOnClickListener {
            val email = binding.inputEmail.text.toString()

            resetError()

            if(validation(email))
            {
                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        toast("Email sent successful")
                        finish()
                    }
                    .addOnFailureListener { ex->
                        toast(ex.message.toString())
                    }
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun resetError() {
        binding.errorEmail.text = null
    }

    private fun validation(email: String): Boolean {

        var isCorrect = true

        //email validation
        if (email.isBlank()) {
            binding.errorEmail.text = getString(R.string.email_blank)
            isCorrect = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.errorEmail.text = getString(R.string.invalid_email)
            isCorrect = false
        }

        return isCorrect
    }
}