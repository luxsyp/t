package john.snow.rickandmorty.detail.repository

import john.snow.rickandmorty.model.RMCharacter


interface CharacterDetailsRepository {

    @Throws(CharacterDetailsException::class)
    fun getCharacter(characterId: Int): RMCharacter

    class CharacterDetailsException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause)
}