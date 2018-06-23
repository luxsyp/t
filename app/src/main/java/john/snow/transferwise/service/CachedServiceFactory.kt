package john.snow.transferwise.service

import john.snow.dependency.ExecutorFactory
import john.snow.dependency.ServiceFactory
import john.snow.rickandmorty.api.RMService
import john.snow.rickandmorty.db.CharacterDb
import john.snow.transferwise.rm.api.RMCachedService
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
            is RMService -> RMCachedService(executorFactory, characterDb, service) as S
            else -> service
        }
    }
}