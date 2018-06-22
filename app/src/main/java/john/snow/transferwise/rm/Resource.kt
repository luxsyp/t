package john.snow.transferwise.rm

sealed class Resource<T> {
    data class Progress<T>(var data: T?) : Resource<T>()
    data class Success<T>(var data: T) : Resource<T>()
    data class Failure<T>(val error: Throwable) : Resource<T>()

    companion object {
        fun <T> loading(data: T?): Resource<T> = Progress(data)
        fun <T> success(data: T): Resource<T> = Success(data)
        fun <T> failure(e: Throwable): Resource<T> = Failure(e)
    }
}
