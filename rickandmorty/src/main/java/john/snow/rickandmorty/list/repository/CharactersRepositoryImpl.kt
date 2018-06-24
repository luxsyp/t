package john.snow.rickandmorty.list.repository

import john.snow.rickandmorty.api.RMListService
import john.snow.rickandmorty.model.ApiEmptyResponse
import john.snow.rickandmorty.model.ApiErrorResponse
import john.snow.rickandmorty.model.ApiSuccessResponse
import john.snow.rickandmorty.model.RMCharacter
import java.util.regex.Pattern

class CharactersRepositoryImpl(
        private val listService: RMListService
) : CharactersRepository {

    private var nextPage: Int? = null

    override fun getCharacters(): List<RMCharacter> {
        val response = listService.getCharacters()
        when (response) {
            is ApiSuccessResponse -> {
                response.body.info?.next?.let { nextPage = findNextPageNumber(it) }
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
        nextPage?.let { next ->
            val response = listService.getCharacters(next)
            when (response) {
                is ApiSuccessResponse -> {
                    response.body.info?.next?.let { nextPage = findNextPageNumber(it) }
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


    private fun findNextPageNumber(nextPageUrl: String): Int? {
        val matcher = PAGE_PATTERN.matcher(nextPageUrl)
        if (matcher.find() && matcher.groupCount() == 1) {
            try {
                return Integer.parseInt(matcher.group(1))
            } catch (ex: NumberFormatException) {
                // do nothing
            }
        }
        return null
    }

    companion object {
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
    }
}