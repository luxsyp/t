package john.snow.rickandmorty.detail.repository

import john.snow.rickandmorty.api.RMDetailService
import john.snow.rickandmorty.model.ApiEmptyResponse
import john.snow.rickandmorty.model.ApiErrorResponse
import john.snow.rickandmorty.model.ApiSuccessResponse
import john.snow.rickandmorty.model.RMCharacter


class CharacterDetailsRepositoryImpl(
        private val service: RMDetailService
) : CharacterDetailsRepository {

    override fun getCharacter(characterId: Int): RMCharacter {
        val response = service.getCharacters(characterId)
        when (response) {
            is ApiSuccessResponse -> {
                return response.body
            }
            is ApiEmptyResponse -> {
                throw CharacterDetailsRepository.CharacterDetailsException("Missing character")
            }
            is ApiErrorResponse -> {
                throw CharacterDetailsRepository.CharacterDetailsException(cause = response.cause)
            }
        }
    }

}
