package bigboss.team.learnpenguin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import bigboss.team.learnpenguin.databinding.ActivityLoginBinding
import bigboss.team.learnpenguin.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.txtbtnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.txtbtnForgetPass.setOnClickListener {
            val intent = Intent(this, ForgetPassActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            resetError()

            if(validation(email, password))
            {
                login(email, password)
            }
        }
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                toast("Login successful")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { ex->
                toast(ex.message.toString())
            }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun resetError() {
        binding.errorEmail.text = null
        binding.errorPassword.text = null
    }

    private fun validation(email: String, password: String): Boolean {

        var isCorrect = true

        //email validation
        if (email.isBlank()) {
            binding.errorEmail.text = getString(R.string.email_blank)
            isCorrect = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.errorEmail.text = getString(R.string.invalid_email)
            isCorrect = false
        }

        //password validation
        val pattern: Pattern
        pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&+-])[A-Za-z\\d@$!%*?&+-]{8,}$")

        if (!pattern.matcher(password).matches()) {
            binding.errorPassword.text ="At least 1 lowercase, uppercase, digit,\n special character and 8 characters."
            isCorrect = false
        }

        return isCorrect
    }
}