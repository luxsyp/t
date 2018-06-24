package john.snow.rickandmorty.detail.presentation

import john.snow.rickandmorty.model.RMCharacter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
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

    @Test
    fun testPresentCharacter() {
        val character = mock(RMCharacter::class.java)

        presenter.presentCharacter(character)

        verify(view).displayCharacter(character)
    }

    @Test
    fun testPresentCharacterError() {
        val error = mock(Throwable::class.java)

        presenter.presentCharacterError(error)

        verify(view).displayCharacterError()
    }
}