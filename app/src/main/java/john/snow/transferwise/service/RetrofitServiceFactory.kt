package john.snow.transferwise.service

import john.snow.dependency.ServiceFactory
import john.snow.rickandmorty.api.RMService
import retrofit2.Retrofit
import kotlin.reflect.KClass

class RetrofitServiceFactory(retrofit: Retrofit) : ServiceFactory {

    private val map: Map<KClass<out Any>, () -> Any> = mapOf(
            RMService::class to { retrofit.create(RMService::class.java) }
    )

    @Suppress("UNCHECKED_CAST")
    override fun <S : Any> get(kClass: KClass<S>): S {
        val builder: (() -> Any)? = map[kClass]
        return builder?.let {
            it.invoke() as S
        } ?: throw IllegalArgumentException("can not found builder $kClass")
    }
}