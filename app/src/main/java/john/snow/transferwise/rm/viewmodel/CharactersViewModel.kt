package john.snow.transferwise.rm.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import john.snow.rickandmorty.model.RMCharacter
import john.snow.transferwise.rm.CharactersRepository
import john.snow.transferwise.rm.Resource

class CharactersViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {

    val charactersResource: LiveData<Resource<List<RMCharacter>>> by lazy {
        charactersRepository.getCharacters()
    }

    fun getCharacters() {
        if (charactersResource.value == null) {
            charactersRepository.getCharacters()
        }
    }

}