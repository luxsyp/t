package john.snow.transferwise.service

import john.snow.dependency.ExecutorFactory
import john.snow.dependency.ServiceFactory
import john.snow.rickandmorty.api.RMDetailService
import john.snow.rickandmorty.api.RMListService
import john.snow.rickandmorty.api.cache.RMDetailCachedService
import john.snow.rickandmorty.api.cache.RMListCachedService
import john.snow.rickandmorty.db.CharacterDb
import kotlin.reflect.KClass

class CachedServiceFactory(
        private val executorFactory: ExecutorFactory,
        private val characterDb: CharacterDb,
        private val serviceFactory: ServiceFactory
) : ServiceFactory {

    @Suppress("UNCHECKED_CAST")
    override fun <S : Any> get(kClass: KClass<S>): S {
        val service = serviceFactory.get(kClass)
        return when (service) {
            is RMListService -> RMListCachedService(executorFactory, characterDb, service) as S
            is RMDetailService-> RMDetailCachedService(executorFactory, characterDb, service) as S
            else -> service
        }
    }
}