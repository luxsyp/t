package john.snow.transferwise

import android.arch.persistence.room.Room
import android.content.Context
import john.snow.dependency.ExecutorFactory
import john.snow.dependency.ServiceFactory
import john.snow.rickandmorty.api.RMService
import john.snow.rickandmorty.db.CharacterDb
import john.snow.rickandmorty.factory.CharactersModuleFactory
import john.snow.rickandmorty.factory.RetrofitServiceFactory
import john.snow.transferwise.executor.ExecutorFactoryImpl
import john.snow.transferwise.service.CachedServiceFactory

@Suppress("JoinDeclarationAndAssignment")
class DependencyManager(context: Context) {

    val executorFactory: ExecutorFactory
    val database: CharacterDb
    val serviceFactory: ServiceFactory
    val charactersModuleFactory: CharactersModuleFactory

    init {
        executorFactory = ExecutorFactoryImpl()

        database = Room.databaseBuilder(context, CharacterDb::class.java, CharacterDb.DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

        val retrofitServiceFactory = RetrofitServiceFactory()
        serviceFactory = CachedServiceFactory(executorFactory, database, retrofitServiceFactory)

        charactersModuleFactory = CharactersModuleFactory(
                executorFactory,
                serviceFactory.get(RMService::class))
    }
}