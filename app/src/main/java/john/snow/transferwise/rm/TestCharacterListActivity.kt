package john.snow.transferwise.rm

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import john.snow.dependency.ExecutorFactory
import john.snow.dependency.Injection
import john.snow.dependency.ServiceFactory
import john.snow.rickandmorty.api.RMService
import john.snow.transferwise.R
import john.snow.transferwise.rm.viewmodel.CharactersViewModel
import john.snow.transferwise.rm.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_list.*


class TestCharacterListActivity : AppCompatActivity() {

    private lateinit var charactersViewModel: CharactersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // DI
        val executorFactory = Injection.get(ExecutorFactory::class)
        val serviceFactory = Injection.get(ServiceFactory::class)
        val rmService = serviceFactory.get(RMService::class)

        val charactersRepository = CharactersRepositoryImpl(executorFactory, rmService)
        val viewModelFactory = ViewModelFactory(charactersRepository)
        charactersViewModel = ViewModelProviders.of(this, viewModelFactory).get(CharactersViewModel::class.java)

        // Ui
        charactersViewModel.charactersResource.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Progress -> {
//                    swipeRefreshLayout.isRefreshing = true
//                    resource.data?.run { textView.text = map { it.name }.toString() }
                }
                is Resource.Success -> {
//                    swipeRefreshLayout.isRefreshing = false
//                    textView.text = resource.data?.map { it.name }.toString()
                }
                is Resource.Failure -> {
                    Toast.makeText(this@TestCharacterListActivity, resource.error.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        charactersViewModel.getCharacters()
    }

}