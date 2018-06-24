package john.snow.rickandmorty.detail.interactor

import john.snow.rickandmorty.detail.presentation.CharacterDetailsPresenter
import john.snow.rickandmorty.detail.repository.CharacterDetailsRepository
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CharacterDetailsInteractorImplTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var presenter: CharacterDetailsPresenter
    @Mock
    private lateinit var repository: CharacterDetailsRepository

    private lateinit var interactor: CharacterDetailsInteractorImpl

    @Before
    fun setUp() {
        interactor = CharacterDetailsInteractorImpl(repository, presenter)
    }


}