package john.snow.transferwise.rm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import john.snow.dependency.ExecutorFactory
import john.snow.transferwise.rm.api.ApiEmptyResponse
import john.snow.transferwise.rm.api.ApiErrorResponse
import john.snow.transferwise.rm.api.ApiResponse
import john.snow.transferwise.rm.api.ApiSuccessResponse

abstract class DbNetworkResourceBinder<ResultType, RequestType>
@MainThread constructor(private val executorFactory: ExecutorFactory) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                executorFactory.getWorkerThread().execute {
                    fetchFromNetwork(dbSource)
                }
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            executorFactory.getUiThread().execute { result.value = newValue }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }

        val apiResponse = fetchCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly

        when (apiResponse) {
            is ApiSuccessResponse -> {
                executorFactory.getDiskIOThread().execute {
                    saveCallResult(processResponse(apiResponse))
                    executorFactory.getUiThread().execute {
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
            }
            is ApiEmptyResponse -> {
                executorFactory.getUiThread().execute {
                    // reload from disk whatever we had
                    result.addSource(loadFromDb()) { newData ->
                        setValue(Resource.success(newData))
                    }
                }
            }
            is ApiErrorResponse -> {
                onFetchFailed()
                result.addSource(dbSource) {
                    setValue(Resource.failure(Throwable(apiResponse.errorMessage)))
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun fetchCall(): ApiResponse<RequestType>

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    fun asLiveData() = result as LiveData<Resource<ResultType>>

}