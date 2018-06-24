package john.snow.rickandmorty.api.cache

import john.snow.dependency.ExecutorFactory
import john.snow.rickandmorty.api.RMDetailService
import john.snow.rickandmorty.db.CharacterDb
import john.snow.rickandmorty.model.ApiResponse
import john.snow.rickandmorty.model.ApiSuccessResponse
import john.snow.rickandmorty.model.RMCharacter
import retrofit2.Response

class RMDetailCachedService(
        private val executorFactory: ExecutorFactory,
        private val characterDb: CharacterDb,
        private val retrofitDetailService: RMDetailService
) : RMDetailService by retrofitDetailService {

    override fun getCharacter(characterId: Int): ApiResponse<RMCharacter> {
        val characterDAO = characterDb.characterDAO()
        val character = characterDAO.load(characterId)
        if (character != null) {
            return ApiResponse.create(Response.success(character))
        }
        return retrofitDetailService.getCharacter(characterId).also { response ->
            if (response is ApiSuccessResponse) {
                executorFactory.getDiskIOThread().execute {
                    characterDAO.insert(listOf(response.body))
                }
            }
        }
    }
}