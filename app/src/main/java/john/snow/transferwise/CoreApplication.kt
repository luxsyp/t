package john.snow.transferwise

import android.app.Application

class CoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val dependencyManager = DependencyManager(applicationContext)
        val injectionMapper = InjectionMapper(dependencyManager)
        injectionMapper.inject()
    }
}