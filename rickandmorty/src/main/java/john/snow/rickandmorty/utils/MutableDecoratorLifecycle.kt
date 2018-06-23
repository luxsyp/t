package john.snow.rickandmorty.utils

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import com.nicolasmouchel.executordecorator.MutableDecorator

@Suppress("unused")
class MutableDecoratorLifecycle<T>(
        private val decorated: T,
        private val decorator: MutableDecorator<T>
) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        decorator.mutate(decorated)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        decorator.mutate(null)
    }
}

@Suppress("unused")
fun <T> LifecycleOwner.addDecorator(decorated: T, decorator: MutableDecorator<T>) {
    lifecycle.addObserver(MutableDecoratorLifecycle(decorated, decorator))
}