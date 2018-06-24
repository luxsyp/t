package john.snow.rickandmorty.detail.module

import com.nicolasmouchel.executordecorator.ImmutableExecutorDecorator
import com.nicolasmouchel.executordecorator.MutableDecorator
import com.nicolasmouchel.executordecorator.MutableExecutorDecorator
import john.snow.dependency.ExecutorFactory
import john.snow.rickandmorty.api.RMDetailService
import john.snow.rickandmorty.detail.interactor.CharacterDetailsInteractor
import john.snow.rickandmorty.detail.interactor.CharacterDetailsInteractorImpl
import john.snow.rickandmorty.detail.presentation.CharacterDetailsPresenterImpl
import john.snow.rickandmorty.detail.presentation.CharacterDetailsView
import john.snow.rickandmorty.detail.repository.CharacterDetailsRepositoryImpl

class CharacterDetailsModule(
        private val executorFactory: ExecutorFactory,
        private val service: RMDetailService
) {

    val viewDecorator: MutableDecorator<CharacterDetailsView>
    val interactor: CharacterDetailsInteractor

    init {
        viewDecorator = provideView()
        interactor = provideInteractor()
    }

    @MutableExecutorDecorator
    private fun provideView(): MutableDecorator<CharacterDetailsView> =
            CharacterDetailsViewDecorator(executorFactory.getUiThread())

    @ImmutableExecutorDecorator
    private fun provideInteractor(): CharacterDetailsInteractor {
        val presenter = CharacterDetailsPresenterImpl(viewDecorator.asDecorated())
        val repository = CharacterDetailsRepositoryImpl(service)
        val interactorImpl = CharacterDetailsInteractorImpl(repository, presenter)
        return CharacterDetailsInteractorDecorator(executorFactory.getWorkerThread(), interactorImpl)
    }
}