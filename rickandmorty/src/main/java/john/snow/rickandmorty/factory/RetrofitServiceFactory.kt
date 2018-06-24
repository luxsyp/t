package john.snow.rickandmorty.factory

import john.snow.dependency.ServiceFactory
import john.snow.rickandmorty.api.ApiResponseAdapterFactory
import john.snow.rickandmorty.api.RMDetailService
import john.snow.rickandmorty.api.RMListService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

class RetrofitServiceFactory : ServiceFactory {

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseAdapterFactory())
            .build()

    private val map: Map<KClass<out Any>, () -> Any> = mapOf(
            RMListService::class to { retrofit.create(RMListService::class.java) },
            RMDetailService::class to { retrofit.create(RMDetailService::class.java) }
    )

    @Suppress("UNCHECKED_CAST")
    override fun <S : Any> get(kClass: KClass<S>): S {
        val builder: (() -> Any)? = map[kClass]
        return builder?.let {
            it.invoke() as S
        } ?: throw IllegalArgumentException("can not found builder $kClass")
    }

    companion object {
        const val ENDPOINT = "https://rickandmortyapi.com/api/"
    }
}