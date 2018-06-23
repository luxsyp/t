package john.snow.rickandmorty.list

import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator
import com.nicolasmouchel.executordecorator.MutableDecorator
import com.nicolasmouchel.executordecorator.MutableExecutorDecorator
import john.snow.dependency.ExecutorFactory
import john.snow.rickandmorty.api.RMService

class CharactersListModule(
        private val executorFactory: ExecutorFactory,
        private val service: RMService
) {

    val viewDecorator: MutableDecorator<CharactersView>
    val interactor: CharactersInteractor

    init {
        viewDecorator = provideView()
        interactor = provideInteractor(viewDecorator.asDecorated())
    }

    @ImmutableExecutorDecorator
    private fun provideInteractor(view: CharactersView): CharactersInteractor {
        val presenter: CharactersPresenter = CharactersPresenterImpl(view)
        val repository: CharactersRepository = CharactersRepositoryImpl(service)
        val interactor: CharactersInteractor = CharactersInteractorImpl(presenter, repository)
        return CharactersInteractorDecorator(executorFactory.getWorkerThread(), interactor)
    }

    @MutableExecutorDecorator
    private fun provideView(): MutableDecorator<CharactersView> {
        return CharactersViewDecorator(executorFactory.getUiThread())
    }
}