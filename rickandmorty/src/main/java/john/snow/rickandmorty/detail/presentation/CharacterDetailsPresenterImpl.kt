package john.snow.rickandmorty.detail.presentation

import android.util.Log
import john.snow.rickandmorty.model.RMCharacter


class CharacterDetailsPresenterImpl(
        private val view: CharacterDetailsView
) : CharacterDetailsPresenter {
    
    override fun presentCharacter(character: RMCharacter) {
        view.displayCharacter(character)
    }

    override fun presentCharacterError(cause: Throwable) {
        Log.e("CharacterDetailsPresent", "presentCharacterError", cause)
        view.displayCharacterError()
    }
}