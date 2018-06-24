package john.snow.transferwise.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import john.snow.rickandmorty.ui.list.CharacterListActivity
import john.snow.transferwise.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler().postDelayed({
            CharacterListActivity.start(this)
        }, DELAY_SPLASH_SCREEN)
    }

    private companion object {
        private const val DELAY_SPLASH_SCREEN = 3000L
    }
}