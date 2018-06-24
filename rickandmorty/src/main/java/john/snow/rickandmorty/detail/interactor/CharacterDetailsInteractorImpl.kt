package john.snow.rickandmorty.detail.interactor

import john.snow.rickandmorty.detail.presentation.CharacterDetailsPresenter
import john.snow.rickandmorty.detail.repository.CharacterDetailsRepository

class CharacterDetailsInteractorImpl(
        private val repository: CharacterDetailsRepository,
        private val presenter: CharacterDetailsPresenter
) : CharacterDetailsInteractor {

    override fun getCharacter(characterId: Int) {
        try {
            val character = repository.getCharacter(characterId)
            presenter.presentCharacter(character)
        } catch (e: CharacterDetailsRepository.CharacterDetailsException) {
            presenter.presentCharacterError(e)
        }
    }
}