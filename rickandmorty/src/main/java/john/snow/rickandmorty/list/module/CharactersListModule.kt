package john.snow.rickandmorty.list.module

import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator
import com.nicolasmouchel.executordecorator.MutableDecorator
import com.nicolasmouchel.executordecorator.MutableExecutorDecorator
import john.snow.dependency.ExecutorFactory
import john.snow.rickandmorty.api.RMService
import john.snow.rickandmorty.list.*
import john.snow.rickandmorty.list.interactor.CharactersInteractor
import john.snow.rickandmorty.list.interactor.CharactersInteractorImpl
import john.snow.rickandmorty.list.presentation.CharactersPresenter
import john.snow.rickandmorty.list.presentation.CharactersPresenterImpl
import john.snow.rickandmorty.list.presentation.CharactersView
import john.snow.rickandmorty.list.repository.CharactersRepository
import john.snow.rickandmorty.list.repository.CharactersRepositoryImpl

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