package john.snow.rickandmorty.detail.presentation

import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CharacterDetailsPresenterImplTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var view: CharacterDetailsView

    private lateinit var presenter: CharacterDetailsPresenterImpl

    @Before
    fun setUp() {
        presenter = CharacterDetailsPresenterImpl(view)
    }


}