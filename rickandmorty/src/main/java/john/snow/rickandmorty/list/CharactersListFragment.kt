package john.snow.rickandmorty.list

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nicolasmouchel.executordecorator.MutableDecorator
import john.snow.dependency.ExecutorFactory
import john.snow.dependency.Injection
import john.snow.rickandmorty.R
import john.snow.rickandmorty.api.RMService
import john.snow.rickandmorty.model.RMCharacter
import john.snow.rickandmorty.utils.addDecorator
import kotlinx.android.synthetic.main.fragment_characters_list.*
import retrofit2.Retrofit

class CharactersListFragment : Fragment() {

    private lateinit var interactor: CharactersInteractor
    private lateinit var viewDecorator: MutableDecorator<CharactersView>

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val executorFactory = Injection.get(ExecutorFactory::class)

        val retrofit = Injection.get(Retrofit::class)
//        val characterDb = Injection.get(CharacterDb::class)

        val rmService = retrofit.create(RMService::class.java)
//        val cachedService = RMCachedService(executorFactory, characterDb, rmService)

        val charactersListModule = CharactersListModule(executorFactory, rmService)
        interactor = charactersListModule.interactor
        viewDecorator = charactersListModule.viewDecorator
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_characters_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addDecorator(CharactersViewImpl(), viewDecorator)
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

        override fun displayNextCharactersError() {
        }

        override fun displayNextCharactersEmpty() {
        }
    }
}