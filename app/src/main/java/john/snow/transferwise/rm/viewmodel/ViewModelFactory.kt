package john.snow.transferwise.rm.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import john.snow.transferwise.rm.CharactersRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val charactersRepository: CharactersRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(charactersRepository) as T
        }
        throw IllegalArgumentException("unexpected class $modelClass")
    }
}