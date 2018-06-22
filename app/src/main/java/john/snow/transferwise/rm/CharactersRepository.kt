package john.snow.transferwise.rm

import android.arch.lifecycle.LiveData
import john.snow.dependency.ExecutorFactory
import john.snow.transferwise.rm.api.ApiResponse
import john.snow.transferwise.rm.api.RMService
import john.snow.transferwise.rm.db.CharacterDAO
import john.snow.transferwise.rm.db.CharacterDb
import john.snow.transferwise.rm.model.Character
import john.snow.transferwise.rm.model.CharacterResponse

interface CharactersRepository {
    fun getCharacters(): LiveData<Resource<List<Character>>>

    fun getCharacters(page: Int): LiveData<Resource<List<Character>>>
}

class CharactersRepositoryImpl(
        private val executorFactory: ExecutorFactory,
        private val rmService: RMService,
        private val characterDAO: CharacterDAO,
        private val characterDb: CharacterDb
) : CharactersRepository {

    override fun getCharacters(): LiveData<Resource<List<Character>>> {
        return object : DbNetworkResourceBinder<List<Character>, CharacterResponse>(executorFactory) {
            override fun saveCallResult(item: CharacterResponse) {
                characterDb.beginTransaction()
                try {
                    // save Page with pagination too
                    item.results?.let { characterDAO.insert(it) }
                    characterDb.setTransactionSuccessful()
                } finally {
                    characterDb.endTransaction()
                }
            }

            override fun shouldFetch(data: List<Character>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun fetchCall(): ApiResponse<CharacterResponse> {
                return rmService.getCharacters()
            }

            override fun loadFromDb(): LiveData<List<Character>> {
                return characterDAO.loadAll()
            }
        }.asLiveData()
    }

    override fun getCharacters(page: Int): LiveData<Resource<List<Character>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}