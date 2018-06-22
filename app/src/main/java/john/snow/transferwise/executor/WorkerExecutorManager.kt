package john.snow.transferwise.executor

import java.util.concurrent.*

class WorkerExecutorManager() {
    private val threadPoolExecutor: ThreadPoolExecutor

    val executor: Executor
        get() = threadPoolExecutor

    val executorService: ExecutorService
        get() = threadPoolExecutor

    init {
        val workQueue = LinkedBlockingQueue<Runnable>()
        this.threadPoolExecutor = ThreadPoolExecutor(
                NUMBER_OF_CORES,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workQueue)
    }

    private companion object {
        private const val NUMBER_OF_CORES = 3
        private const val MAX_POOL_SIZE = 5
        private const val KEEP_ALIVE_TIME = 120L
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }
}
