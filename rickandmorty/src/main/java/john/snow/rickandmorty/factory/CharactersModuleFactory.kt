package john.snow.rickandmorty.factory

import john.snow.dependency.ExecutorFactory
import john.snow.rickandmorty.api.RMService
import john.snow.rickandmorty.list.module.CharactersListModule

class CharactersModuleFactory(
        private val executorFactory: ExecutorFactory,
        private val rmService: RMService
) {

    fun getCharactersListModule() = CharactersListModule(executorFactory, rmService)
}