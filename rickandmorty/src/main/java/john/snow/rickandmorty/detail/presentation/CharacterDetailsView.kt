package john.snow.rickandmorty.detail.presentation

import john.snow.rickandmorty.model.RMCharacter


interface CharacterDetailsView {
    fun displayCharacter(character: RMCharacter)
    fun displayCharacterError()
}