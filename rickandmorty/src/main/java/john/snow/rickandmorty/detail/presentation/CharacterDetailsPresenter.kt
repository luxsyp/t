package john.snow.rickandmorty.detail.presentation

import john.snow.rickandmorty.model.RMCharacter


interface CharacterDetailsPresenter {
    fun presentCharacter(character: RMCharacter)
    fun presentCharacterError(cause: Throwable)
}