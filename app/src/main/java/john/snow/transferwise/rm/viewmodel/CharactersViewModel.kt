package john.snow.transferwise.rm.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import john.snow.transferwise.rm.CharactersRepository
import john.snow.transferwise.rm.Resource
import john.snow.transferwise.rm.model.Character

class CharactersViewModel(private val charactersRepository: CharactersRepository) : ViewModel() {

    val charactersResource: LiveData<Resource<List<Character>>> by lazy {
        charactersRepository.getCharacters()
    }

    fun getCharacters() {
        if (charactersResource.value == null) {
            charactersRepository.getCharacters()
        }
    }

}