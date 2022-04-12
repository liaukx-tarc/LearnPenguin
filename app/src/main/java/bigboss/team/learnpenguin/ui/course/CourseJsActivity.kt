package bigboss.team.learnpenguin.ui.course

import com.google.android.youtube.player.YouTubeBaseActivity
import android.os.Bundle
import bigboss.team.learnpenguin.R
import com.google.android.youtube.player.YouTubeInitializationResult
import android.widget.Toast
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class CourseJsActivity : YouTubeBaseActivity() {
    var youTubePlayerView: YouTubePlayerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_js)
        youTubePlayerView = findViewById(R.id.youtube_player_view)
        val youTubePlayerView = YouTubePlayerView(this)
        val listener: YouTubePlayer.OnInitializedListener =
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer,
                    b: Boolean
                ) {
                    youTubePlayer.loadVideo("1HakS7KsbCk")
                    youTubePlayer.play()
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                    Toast.makeText(
                        applicationContext,
                        "Initialization Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        youTubePlayerView.initialize(getString(R.string.api_key), listener)
    }
}