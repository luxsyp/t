package john.snow.transferwise

import john.snow.dependency.ExecutorFactory
import john.snow.dependency.Injection

class InjectionMapper(private val dependencyManager: DependencyManager) {

    fun inject() {
        Injection.set(ExecutorFactory::class, dependencyManager.executorFactory)
    }
}