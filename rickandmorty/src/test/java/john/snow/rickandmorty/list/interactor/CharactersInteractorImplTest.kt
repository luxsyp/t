package john.snow.rickandmorty.list.interactor

import john.snow.rickandmorty.list.presentation.CharactersPresenter
import john.snow.rickandmorty.list.repository.CharactersRepository
import john.snow.rickandmorty.model.RMCharacter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit

class CharactersInteractorImplTest {

    @get:Rule
    var mockito: MethodRule = MockitoJUnit.rule()

    @Mock
    private lateinit var repository: CharactersRepository

    @Mock
    private lateinit var presenter: CharactersPresenter

    private lateinit var interactor: CharactersInteractor

    @Before
    fun setUp() {
        interactor = CharactersInteractorImpl(presenter, repository)
    }

    @Test
    fun testGetCharacters_WhenRepositoryThrowException() {
        val exception = CharactersRepository.CharactersException()
        given(repository.getCharacters()).willThrow(exception)

        interactor.getCharacters()

        verify(presenter).presentCharactersError(exception)
    }

    @Test
    fun testGetCharacters() {
        val result = listOf<RMCharacter>(
                mock(RMCharacter::class.java),
                mock(RMCharacter::class.java)
        )
        given(repository.getCharacters()).willReturn(result)

        interactor.getCharacters()

        verify(presenter).presentCharacters(result)
    }

    @Test
    fun testGetCharactersNextPage_WhenRepositoryThrowException() {
        val exception = CharactersRepository.CharactersException()
        given(repository.getCharactersNextPage()).willThrow(exception)

        interactor.getCharactersNextPage()

        verify(presenter).presentNextCharactersError(exception)
    }

    @Test
    fun testGetCharactersNextPage_WhenEndReached() {
        val result = emptyList<RMCharacter>()
        given(repository.getCharactersNextPage()).willReturn(result)

        interactor.getCharactersNextPage()

        verify(presenter).presentNextCharactersEndReached()
    }

    @Test
    fun testGetCharactersNextPage() {
        val result = listOf<RMCharacter>(
                mock(RMCharacter::class.java),
                mock(RMCharacter::class.java)
        )
        given(repository.getCharactersNextPage()).willReturn(result)

        interactor.getCharactersNextPage()

        verify(presenter).presentNextCharacters(result)
    }


}