package john.snow.transferwise.rm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import john.snow.dependency.ExecutorFactory
import john.snow.transferwise.rm.api.*
import john.snow.transferwise.rm.db.CharacterDAO
import john.snow.transferwise.rm.model.Character

interface CharactersRepository {
    fun getCharacters(): LiveData<Resource<List<Character>>>

    fun getCharacters(page: Int): LiveData<Resource<List<Character>>>
}

class CharactersRepositoryImpl(
        private val executorFactory: ExecutorFactory,
        private val rmService: RMService,
        private val characterDAO: CharacterDAO
) : CharactersRepository {
    private val result: MutableLiveData<Resource<List<Character>>> = MutableLiveData()

    override fun getCharacters(): LiveData<Resource<List<Character>>> {
        executorFactory.getWorkerThread().execute {
            val dbCharacters = characterDAO.loadAll()
            if (dbCharacters.isNotEmpty()) {
                result.postValue(Resource.success(dbCharacters))
            } else {
                val response = rmService.getCharacters()
                when (response) {
                    is ApiSuccessResponse -> {
                        response.body.results?.let {
                            saveCharacters(it)
                            result.postValue(Resource.success(it))
                        }
                    }
                    is ApiEmptyResponse -> {
                        result.postValue(Resource.success(emptyList()))
                    }
                    is ApiErrorResponse -> {
                        result.postValue(Resource.failure(Throwable(response.errorMessage)))
                    }
                }
            }
        }
        return result
    }

    private fun saveCharacters(characters: List<Character>) {
        executorFactory.getDiskIOThread().execute {
            characterDAO.insert(characters)
        }
    }

    override fun getCharacters(page: Int): LiveData<Resource<List<Character>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}