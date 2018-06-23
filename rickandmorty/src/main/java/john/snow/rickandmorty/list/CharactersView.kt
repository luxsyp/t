package john.snow.rickandmorty.list

import john.snow.rickandmorty.model.RMCharacter

interface CharactersView {
    fun displayCharacters(characters: List<RMCharacter>)
    fun displayCharactersEmpty()
    fun displayCharactersError()

    fun displayNextCharacters(characters: List<RMCharacter>)
    fun displayNextCharactersError()
    fun displayNextCharactersEmpty()
}