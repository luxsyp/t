package john.snow.rickandmorty.list

import john.snow.rickandmorty.api.RMService
import john.snow.rickandmorty.model.*

interface CharactersRepository {
    fun getCharacters(): List<RMCharacter>
    fun getNextPage(): List<RMCharacter>

    class CharactersException(message: String? = null, cause: Throwable? = null) : Throwable(message, cause)
}

class CharactersRepositoryImpl(
        private val service: RMService
) : CharactersRepository {

    private val nextPageUrl: String? = null

    override fun getCharacters(): List<RMCharacter> {
        val response = service.getCharacters()
        when (response) {
            is ApiSuccessResponse -> {
                response.body.results?.let {
                    return it
                } ?: throw CharactersRepository.CharactersException(message = "empty body")
            }
            is ApiEmptyResponse -> {
                return emptyList()
            }
            is ApiErrorResponse -> {
                throw CharactersRepository.CharactersException(cause = response.cause)
            }
        }
    }

    override fun getNextPage(): List<RMCharacter> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}