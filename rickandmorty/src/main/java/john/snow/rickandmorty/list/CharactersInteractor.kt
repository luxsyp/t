package john.snow.rickandmorty.list

interface CharactersInteractor {
    fun getCharacters()
    fun getNextPage()
}

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

    override fun getNextPage() {
        try {
            val characters = repository.getNextPage()
            presenter.presentNextCharacters(characters)
        } catch (e: CharactersRepository.CharactersException) {
            presenter.presentNextCharactersError(e)
        }
    }
}