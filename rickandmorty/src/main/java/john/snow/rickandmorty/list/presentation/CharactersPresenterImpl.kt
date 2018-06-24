package john.snow.rickandmorty.list.presentation

import android.util.Log
import john.snow.rickandmorty.model.RMCharacter

class CharactersPresenterImpl(
        private val view: CharactersView
) : CharactersPresenter {

    override fun presentCharacters(characters: List<RMCharacter>) {
        if (characters.isEmpty()) {
            view.displayCharactersEmpty()
        } else {
            view.displayCharacters(characters)
        }
    }

    override fun presentCharactersError(cause: Throwable) {
        Log.e("CharactersPresenter", "presentCharactersError", cause)
        view.displayCharactersError()
    }

    override fun presentNextCharacters(characters: List<RMCharacter>) {
        if (characters.isEmpty()) {
            view.displayEndListReached()
        } else {
            view.displayNextCharacters(characters)
        }
    }

    override fun presentNextCharactersError(cause: Throwable) {
        Log.e("CharactersPresenter", "presentNextCharactersError", cause)
        view.displayNextCharactersError()
    }

    override fun presentNextCharactersEndReached() {
        view.displayEndListReached()
    }
}