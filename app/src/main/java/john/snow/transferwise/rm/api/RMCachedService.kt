package john.snow.transferwise.rm.api

import john.snow.dependency.ExecutorFactory
import john.snow.rickandmorty.api.RMService
import john.snow.rickandmorty.model.*
import retrofit2.Response
import john.snow.rickandmorty.db.CharacterDb

class RMCachedService(
        private val executorFactory: ExecutorFactory,
        private val characterDb: CharacterDb,
        private val retrofitService: RMService
) : RMService by retrofitService {
    private val characterDAO = characterDb.characterDAO()
    private val characterResponsePageDAO = characterDb.characterResponsePageDAO()

    override fun getCharacters(): ApiResponse<RMCharacterResponse> {
        val pages = characterResponsePageDAO.loadAll()

        if (pages.isNotEmpty()) {
            val ids = mutableListOf<Int>()
            pages.map { ids.addAll(it.repoIds) }
            val characters = characterDAO.load(ids)
            // reconstruct response
            return ApiResponse.create(
                    Response.success(
                            RMCharacterResponse(
                                    RMCharacterResponseInfo(
                                            pages.last().count,
                                            pages.last().next
                                    ),
                                    characters
                            )
                    )
            )
        }
        return retrofitService.getCharacters().also {
            saveResponseInDb(it)
        }
    }

    private fun saveResponseInDb(response: ApiResponse<RMCharacterResponse>) {
        if (response is ApiSuccessResponse) {
            val characterResponse = response.body
            executorFactory.getDiskIOThread().execute {
                characterDb.beginTransaction()
                try {
                    characterResponsePageDAO.insert(
                            RMCharacterResponsePage(
                                    characterResponse.info?.next ?: "last_page",
                                    characterResponse.results?.map { it.id } ?: emptyList(),
                                    characterResponse.info?.count ?: 0
                            )
                    )
                    characterResponse.results?.let { characterDAO.insert(it) }
                    characterDb.setTransactionSuccessful()
                } finally {
                    characterDb.endTransaction()
                }
            }
        }
    }
}