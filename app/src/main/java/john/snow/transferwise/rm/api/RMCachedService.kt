package john.snow.transferwise.rm.api

import john.snow.dependency.ExecutorFactory
import john.snow.rickandmorty.api.RMService
import john.snow.rickandmorty.db.CharacterDb
import john.snow.rickandmorty.model.*
import retrofit2.Response

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
            return reconstructResponse(pages.last(), ids)
        }
        return retrofitService.getCharacters().also { response ->
            saveResponseInDb(response)
        }
    }

    override fun getCharacters(page: Int): ApiResponse<RMCharacterResponse> {
        val foundPage = characterResponsePageDAO.loadByPageNumber(page)
        if (foundPage != null) {
            return reconstructResponse(foundPage, foundPage.repoIds)
        }
        return retrofitService.getCharacters(page).also { response ->
            saveResponseInDb(response, page)
        }
    }

    private fun reconstructResponse(page: RMCharacterResponsePage, ids: List<Int>): ApiResponse<RMCharacterResponse> {
        val characters = characterDAO.load(ids)
        return ApiResponse.create(Response.success(RMCharacterResponse(
                RMCharacterResponseInfo(
                        page.count,
                        page.next
                ),
                characters
        )))
    }

    private fun saveResponseInDb(response: ApiResponse<RMCharacterResponse>, pagePosition: Int = 0) {
        if (response is ApiSuccessResponse) {
            val characterResponse = response.body
            executorFactory.getDiskIOThread().execute {
                characterDb.beginTransaction()
                try {
                    characterResponsePageDAO.insert(
                            RMCharacterResponsePage(
                                    pagePosition,
                                    characterResponse.info?.next,
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