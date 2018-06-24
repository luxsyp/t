package john.snow.rickandmorty.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nicolasmouchel.executordecorator.MutableDecorator
import john.snow.dependency.Injection
import john.snow.rickandmorty.R
import john.snow.rickandmorty.factory.CharactersModuleFactory
import john.snow.rickandmorty.list.interactor.CharactersInteractor
import john.snow.rickandmorty.list.presentation.CharactersView
import john.snow.rickandmorty.model.RMCharacter
import john.snow.rickandmorty.utils.addDecorator
import kotlinx.android.synthetic.main.fragment_characters_list.*

class CharactersListFragment : Fragment() {

    private lateinit var interactor: CharactersInteractor
    private lateinit var viewDecorator: MutableDecorator<CharactersView>

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val charactersModuleFactory = Injection.get(CharactersModuleFactory::class)
        val charactersListModule = charactersModuleFactory.getCharactersListModule()
        interactor = charactersListModule.interactor
        viewDecorator = charactersListModule.viewDecorator
        addDecorator(CharactersViewImpl(), viewDecorator)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_characters_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interactor.getCharacters()
    }

    private inner class CharactersViewImpl : CharactersView {
        override fun displayCharacters(characters: List<RMCharacter>) {
            textViewFragment.text = characters.map { it.name }.toString()
        }

        override fun displayCharactersEmpty() {
        }

        override fun displayCharactersError() {
        }

        override fun displayNextCharacters(characters: List<RMCharacter>) {
        }

        override fun displayEndListReached() {
        }
        override fun displayNextCharactersError() {
        }

    }
}