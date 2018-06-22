package john.snow.api.common

data class NetworkResponse<out T>(
        val isSuccessful: Boolean,
        val body: T,
        val code: Int,
        val errorBody: String? = null,
        val message: String? = null
)

