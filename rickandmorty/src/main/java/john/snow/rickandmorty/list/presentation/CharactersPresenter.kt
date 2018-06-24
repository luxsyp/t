package john.snow.rickandmorty.list.presentation

import john.snow.rickandmorty.model.RMCharacter

interface CharactersPresenter {
    fun presentCharacters(characters: List<RMCharacter>)
    fun presentCharactersError(cause: Throwable)

    fun presentNextCharacters(characters: List<RMCharacter>)
    fun presentNextCharactersError(cause: Throwable)
    fun presentNextCharactersEndReached()
}
