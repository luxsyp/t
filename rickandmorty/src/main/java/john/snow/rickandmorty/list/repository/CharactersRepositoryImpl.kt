package john.snow.rickandmorty.list.repository

import john.snow.rickandmorty.model.ApiEmptyResponse
import john.snow.rickandmorty.model.ApiErrorResponse
import john.snow.rickandmorty.model.ApiSuccessResponse
import john.snow.rickandmorty.model.RMCharacter
import john.snow.rickandmorty.api.RMService

class CharactersRepositoryImpl(
        private val service: RMService
) : CharactersRepository {

    private var nextPageUrl: String? = null

    override fun getCharacters(): List<RMCharacter> {
        val response = service.getCharacters()
        when (response) {
            is ApiSuccessResponse -> {
                nextPageUrl = response.body.info?.next
                response.body.results?.let {
                    return it
                } ?: throw CharactersRepository.CharactersException(message = "empty results")
            }
            is ApiEmptyResponse -> {
                return emptyList()
            }
            is ApiErrorResponse -> {
                throw CharactersRepository.CharactersException(cause = response.cause)
            }
        }
    }

    override fun getCharactersNextPage(): List<RMCharacter> {
        nextPageUrl?.let { nextUrl ->
            val response = service.getCharacters(nextUrl)
            when (response) {
                is ApiSuccessResponse -> {
                    response.body.results?.let {
                        return it
                    } ?: throw CharactersRepository.CharactersException(message = "empty results")
                }
                is ApiEmptyResponse -> {
                    return emptyList()
                }
                is ApiErrorResponse -> {
                    throw CharactersRepository.CharactersException(cause = response.cause)
                }
            }
        } ?: return emptyList()
    }
}