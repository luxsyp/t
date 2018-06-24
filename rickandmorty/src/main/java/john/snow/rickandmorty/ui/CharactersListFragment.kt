package john.snow.rickandmorty.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private val charactersAdapter: CharactersAdapter = CharactersAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val charactersModuleFactory = Injection.get(CharactersModuleFactory::class)
        val charactersListModule = charactersModuleFactory.getCharactersListModule()
        val viewDecorator = charactersListModule.viewDecorator
        addDecorator(CharactersViewImpl(), viewDecorator)
        interactor = charactersListModule.interactor
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_characters_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(context)
        }

        interactor.getCharacters()
    }

    private inner class CharactersViewImpl : CharactersView {
        override fun displayCharacters(characters: List<RMCharacter>) {
            viewFlipper.displayedChild = DISPLAY_LIST
            charactersAdapter.setCharacters(characters)
        }

        override fun displayCharactersEmpty() {
            viewFlipper.displayedChild = DISPLAY_EMPTY
        }

        override fun displayCharactersError() {
            viewFlipper.displayedChild = DISPLAY_ERROR
        }

        override fun displayNextCharacters(characters: List<RMCharacter>) {
            charactersAdapter.addCharacters(characters)
        }

        override fun displayEndListReached() {

        }

        override fun displayNextCharactersError() {

        }
    }

    @Suppress("unused")
    private companion object {
        private const val DISPLAY_LOADING = 0
        private const val DISPLAY_EMPTY = 1
        private const val DISPLAY_ERROR= 2
        private const val DISPLAY_LIST = 3
    }
}