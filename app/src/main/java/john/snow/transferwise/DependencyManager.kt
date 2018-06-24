package john.snow.transferwise

import android.arch.persistence.room.Room
import android.content.Context
import john.snow.dependency.ExecutorFactory
import john.snow.dependency.ServiceFactory
import john.snow.rickandmorty.api.RMService
import john.snow.rickandmorty.db.CharacterDb
import john.snow.rickandmorty.factory.CharactersModuleFactory
import john.snow.transferwise.executor.ExecutorFactoryImpl
import john.snow.transferwise.rm.ApiResponseAdapterFactory
import john.snow.transferwise.service.CachedServiceFactory
import john.snow.transferwise.service.RetrofitServiceFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("JoinDeclarationAndAssignment")
class DependencyManager(context: Context) {

    val executorFactory: ExecutorFactory
    val database: CharacterDb
    val retrofit: Retrofit
    val serviceFactory: ServiceFactory
    val charactersModuleFactory: CharactersModuleFactory

    init {
        executorFactory = ExecutorFactoryImpl()

        database = Room.databaseBuilder(context, CharacterDb::class.java, CharacterDb.DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(RMService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(ApiResponseAdapterFactory())
                .build()

        val retrofitServiceFactory = RetrofitServiceFactory(retrofit)
        serviceFactory = CachedServiceFactory(executorFactory, database, retrofitServiceFactory)

        charactersModuleFactory = CharactersModuleFactory(
                executorFactory,
                serviceFactory.get(RMService::class))
    }
}