package john.snow.rickandmorty.factory

import john.snow.dependency.ExecutorFactory
import john.snow.dependency.ServiceFactory
import john.snow.rickandmorty.api.RMDetailService
import john.snow.rickandmorty.api.RMListService
import john.snow.rickandmorty.detail.module.CharacterDetailsModule
import john.snow.rickandmorty.list.module.CharactersListModule

class CharactersModuleFactory(
        private val executorFactory: ExecutorFactory,
        serviceFactory: ServiceFactory
) {

    private val rmListService: RMListService = serviceFactory.get(RMListService::class)
    private val detailService: RMDetailService = serviceFactory.get(RMDetailService::class)

    fun getCharactersListModule() = CharactersListModule(executorFactory, rmListService)

    fun getCharactersDetailModule() = CharacterDetailsModule(executorFactory, detailService)
}