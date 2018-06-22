package john.snow.transferwise

import john.snow.dependency.ExecutorFactory
import john.snow.dependency.Injection
import john.snow.rickandmorty.db.CharacterDb
import retrofit2.Retrofit

class InjectionMapper(private val dependencyManager: DependencyManager) {

    fun inject() {
        Injection.set(ExecutorFactory::class, dependencyManager.executorFactory)
        Injection.set(CharacterDb::class, dependencyManager.database)
        Injection.set(Retrofit::class, dependencyManager.retrofit)
    }
}