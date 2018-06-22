package john.snow.transferwise

import android.content.Context
import john.snow.dependency.ExecutorFactory
import john.snow.transferwise.executor.ExecutorFactoryImpl

class DependencyManager(private val context: Context) {

    val executorFactory: ExecutorFactory = ExecutorFactoryImpl()

    init {

    }
}