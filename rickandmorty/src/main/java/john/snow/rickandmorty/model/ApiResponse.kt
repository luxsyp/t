package john.snow.rickandmorty.model

import retrofit2.Response

@Suppress("unused")
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error.message ?: "unknown error")
        }

        fun <T> create(networkResponse: Response<T>): ApiResponse<T> {
            return if (networkResponse.isSuccessful) {
                val body = networkResponse.body()
                if (body == null || networkResponse.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(body)
                }
            } else {
                val msg = networkResponse.errorBody().toString()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    networkResponse.message()
                } else {
                    msg
                }
                ApiErrorResponse(errorMsg ?: "unknown error")
            }
        }
    }
}

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()