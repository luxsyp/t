package john.snow.rickandmorty.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import john.snow.dependency.Injection
import john.snow.rickandmorty.R
import john.snow.rickandmorty.detail.interactor.CharacterDetailsInteractor
import john.snow.rickandmorty.detail.presentation.CharacterDetailsView
import john.snow.rickandmorty.factory.CharactersModuleFactory
import john.snow.rickandmorty.model.RMCharacter
import john.snow.rickandmorty.utils.addDecorator
import kotlinx.android.synthetic.main.activity_character_detail_list.*

class CharacterDetailActivity : AppCompatActivity() {
    private lateinit var interactor: CharacterDetailsInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail_list)

        val charactersModuleFactory = Injection.get(CharactersModuleFactory::class)
        val module = charactersModuleFactory.getCharactersDetailModule()
        interactor = module.interactor
        addDecorator(CharacterDetailsViewImpl(), module.viewDecorator)

        retryButton.setOnClickListener {
            viewFlipper.displayedChild = DISPLAY_LOADING
            fetchUser()
        }

        fetchUser()
    }

    private fun fetchUser() {
        if (intent.hasExtra(EXTRA_CHARACTER_ID)) {
            val id = intent.getIntExtra(EXTRA_CHARACTER_ID, 0)
            interactor.getCharacter(id)
        }
    }

    private inner class CharacterDetailsViewImpl : CharacterDetailsView {
        override fun displayCharacter(character: RMCharacter) {
            viewFlipper.displayedChild = DISPLAY_DETAIL
            nameTextView.text = character.name
            statusTextView.text = getString(R.string.character_status, character.status)
            episodesTextView.text = character.episode.joinToString(separator = "") { "$it\n" }
            Picasso.get().load(character.image).into(characterImageView)
        }

        override fun displayCharacterError() {
            viewFlipper.displayedChild = DISPLAY_ERROR
        }
    }

    companion object {
        private const val EXTRA_CHARACTER_ID = "EXTRA_CHARACTER_ID"
        private const val DISPLAY_LOADING = 0
        private const val DISPLAY_ERROR = 1
        private const val DISPLAY_DETAIL = 2

        fun start(context: Context, characterId: Int) {
            val intent = Intent(context, CharacterDetailActivity::class.java)
            intent.putExtra(EXTRA_CHARACTER_ID, characterId)
            context.startActivity(intent)
        }
    }
}