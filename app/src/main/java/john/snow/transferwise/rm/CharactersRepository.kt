package john.snow.transferwise.rm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import john.snow.dependency.ExecutorFactory
import john.snow.rickandmorty.api.RMService
import john.snow.rickandmorty.model.ApiEmptyResponse
import john.snow.rickandmorty.model.ApiErrorResponse
import john.snow.rickandmorty.model.ApiSuccessResponse
import john.snow.rickandmorty.model.RMCharacter

interface CharactersRepository {
    fun getCharacters(): LiveData<Resource<List<RMCharacter>>>

    fun getCharacters(page: Int): LiveData<Resource<List<RMCharacter>>>
}

class CharactersRepositoryImpl(
        private val executorFactory: ExecutorFactory,
        private val RMService: RMService
) : CharactersRepository {
    private val result: MutableLiveData<Resource<List<RMCharacter>>> = MutableLiveData()

    override fun getCharacters(): LiveData<Resource<List<RMCharacter>>> {
        executorFactory.getWorkerThread().execute {
            val response = RMService.getCharacters()
            when (response) {
                is ApiSuccessResponse -> {
                    response.body.results?.let {
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
        return result
    }

    override fun getCharacters(page: Int): LiveData<Resource<List<RMCharacter>>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}