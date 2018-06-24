package john.snow.rickandmorty.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import john.snow.rickandmorty.R


class CharacterListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CharacterListActivity::class.java)
            context.startActivity(intent)
        }
    }
}