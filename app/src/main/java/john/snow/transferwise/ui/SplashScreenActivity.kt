package john.snow.transferwise.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import john.snow.rickandmorty.ui.list.CharacterListActivity
import john.snow.transferwise.R
import kotlinx.android.synthetic.main.activity_splashscreen.*


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val rotateAnimation = RotateAnimation(0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = DELAY_SPLASH_SCREEN
        splashIconImageView.startAnimation(rotateAnimation)


        Handler().postDelayed({
            CharacterListActivity.start(this)
        }, DELAY_SPLASH_SCREEN)
    }

    private companion object {
        private const val DELAY_SPLASH_SCREEN = 3000L
    }
}