package john.snow.rickandmorty.list.presentation

import john.snow.rickandmorty.model.RMCharacter
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.junit.rules.MethodRule
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit

class CharactersPresenterImplTest {

    @get:Rule
    var mockito: MethodRule = MockitoJUnit.rule()

    @Mock
    private lateinit var view: CharactersView

    private lateinit var presenter: CharactersPresenter

    @Before
    fun setUp() {
        presenter = CharactersPresenterImpl(view)
    }

    @Test
    fun testPresentCharacters_WhenListIsEmpty() {

        presenter.presentCharacters(emptyList())

        verify(view).displayCharactersEmpty()
    }

    @Test
    fun testPresentCharacters() {
        val list = listOf(mock(RMCharacter::class.java))

        presenter.presentCharacters(list)

        verify(view).displayCharacters(list)
    }

    @Test
    fun testPresentCharactersError() {
        val error = mock(Throwable::class.java)

        presenter.presentCharactersError(error)

        verify(view).displayCharactersError()
    }

    @Test
    fun testPresentNextCharacters_WhenListIsEmpty() {

        presenter.presentNextCharacters(emptyList())

        verify(view).displayEndListReached()
    }

    @Test
    fun testPresentNextCharacters() {
        val list = listOf(mock(RMCharacter::class.java))

        presenter.presentNextCharacters(list)

        verify(view).displayNextCharacters(list)
    }

    @Test
    fun testPresentNextCharactersError() {
        val error = mock(Throwable::class.java)

        presenter.presentNextCharactersError(error)

        verify(view).displayNextCharactersError()
    }

    @Test
    fun testPresentNextCharactersEndReached() {

        presenter.presentNextCharactersEndReached()

        verify(view).displayEndListReached()
    }
}