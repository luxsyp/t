package john.snow.transferwise.rm

import john.snow.rickandmorty.model.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseAdapterFactory : CallAdapter.Factory() {
    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (CallAdapter.Factory.getRawType(returnType) != ApiResponse::class.java) {
            return null
        }
        val bodyType = CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType)
        return ApiResponseAdapter<Any>(bodyType)
    }
}

class ApiResponseAdapter<R>(private val responseType: Type) : CallAdapter<R, ApiResponse<R>> {

    override fun adapt(call: Call<R>): ApiResponse<R> {
        val response = call.execute()
        return ApiResponse.create(response)
    }

    override fun responseType() = responseType
}