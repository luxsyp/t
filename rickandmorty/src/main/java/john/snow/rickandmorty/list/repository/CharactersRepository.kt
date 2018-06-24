package john.snow.rickandmorty.list.repository

import john.snow.rickandmorty.model.*

interface CharactersRepository {

    @Throws(CharactersException::class)
    fun getCharacters(): List<RMCharacter>

    @Throws(CharactersException::class)
    fun getCharactersNextPage(): List<RMCharacter>

    class CharactersException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause)
}
