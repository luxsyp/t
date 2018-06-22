package john.snow.transferwise.executor

import john.snow.dependency.ExecutorFactory
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ExecutorFactoryImpl : ExecutorFactory {

    private val diskIoThread = Executors.newSingleThreadExecutor()
    private val workerExecutorManager = WorkerExecutorManager()

    override fun getDiskIOThread(): Executor = diskIoThread

    override fun getUiThread(): Executor = UiThreadExecutor()

    override fun getWorkerThread(): ExecutorService = workerExecutorManager.executorService
}