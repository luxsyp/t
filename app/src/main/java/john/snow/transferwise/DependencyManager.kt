package john.snow.transferwise

import android.arch.persistence.room.Room
import android.content.Context
import john.snow.dependency.ExecutorFactory
import john.snow.rickandmorty.db.CharacterDb
import john.snow.transferwise.executor.ExecutorFactoryImpl
import john.snow.transferwise.rm.ApiResponseAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("JoinDeclarationAndAssignment")
class DependencyManager(context: Context) {

    val executorFactory: ExecutorFactory = ExecutorFactoryImpl()
    val database: CharacterDb
    val retrofit: Retrofit

    init {
        database = Room.databaseBuilder(context, CharacterDb::class.java, CharacterDb.DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(ApiResponseAdapterFactory())
                .build()
    }
}