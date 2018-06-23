package john.snow.rickandmorty.list

import android.util.Log
import john.snow.rickandmorty.model.RMCharacter

interface CharactersPresenter {
    fun presentCharacters(characters: List<RMCharacter>)
    fun presentCharactersError(cause: Throwable)

    fun presentNextCharacters(characters: List<RMCharacter>)
    fun presentNextCharactersError(cause: Throwable)
}

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
        Log.e("CharactersPresenter", cause.message)
    }

    override fun presentNextCharacters(characters: List<RMCharacter>) {
        if (characters.isEmpty()) {
            view.displayNextCharactersEmpty()
        } else {
            view.displayNextCharacters(characters)
        }
    }

    override fun presentNextCharactersError(cause: Throwable) {
        Log.e("CharactersPresenter", cause.message)
    }

}