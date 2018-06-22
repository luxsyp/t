package john.snow.dependency

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

interface ExecutorFactory {
    fun getDiskIOThread(): Executor
    fun getUiThread(): Executor
    fun getWorkerThread(): ExecutorService
}