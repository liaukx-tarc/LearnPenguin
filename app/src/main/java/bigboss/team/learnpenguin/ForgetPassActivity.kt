package bigboss.team.learnpenguin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import bigboss.team.learnpenguin.databinding.ActivityForgetPassBinding
import bigboss.team.learnpenguin.databinding.ActivityLoginBinding

class ForgetPassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtbtnRegister.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }

        binding.imgbtnBack.setOnClickListener {
            finish()
        }
    }
}