package john.snow.rickandmorty.list.interactor

import john.snow.rickandmorty.list.presentation.CharactersPresenter
import john.snow.rickandmorty.list.repository.CharactersRepository

class CharactersInteractorImpl(
        private val presenter: CharactersPresenter,
        private val repository: CharactersRepository
) : CharactersInteractor {

    override fun getCharacters() {
        try {
            val characters = repository.getCharacters()
            presenter.presentCharacters(characters)
        } catch (e: CharactersRepository.CharactersException) {
            presenter.presentCharactersError(e)
        }
    }

    override fun getCharactersNextPage() {
        try {
            val characters = repository.getCharactersNextPage()
            if (characters.isNotEmpty()) {
                presenter.presentNextCharacters(characters)
            } else {
                presenter.presentNextCharactersEndReached()
            }
        } catch (e: CharactersRepository.CharactersException) {
            presenter.presentNextCharactersError(e)
        }
    }
}