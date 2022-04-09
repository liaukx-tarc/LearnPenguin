package bigboss.team.learnpenguin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import bigboss.team.learnpenguin.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.txtbtnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnRegister.setOnClickListener {
            resetError()

            val username = binding.inputUsername.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val retypePass = binding.inputRetypePass.text.toString()

            if (validation(username, email, password, retypePass)) {
                createAccount(email, password)
            }
        }

        //binding.inputUsername.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.account, 0, R.drawable.error, 0)
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                toast("Account create successful")
                val intent = Intent(this, LoginActivity::class.java)
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
        binding.errorUsername.text = null
        binding.errorEmail.text = null
        binding.errorPassword.text = null
        binding.errorRetypePass.text = null
    }

    private fun validation(
        username: String,
        email: String,
        password: String,
        retypePass: String
    ): Boolean {

        var isCorrect = true

        //username validation
        if (username.isBlank()) {
            binding.errorUsername.text = getString(R.string.username_blank)
            isCorrect = false
        } else if (username.length > 50) {
            binding.errorUsername.text = getString(R.string.username_More50)
            isCorrect = false
        }

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

        //retype Password validation
        if (retypePass != password) {
            binding.errorRetypePass.text = getString(R.string.retyePass_notSame)
            isCorrect = false
        }

        return isCorrect
    }


}