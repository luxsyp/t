package john.snow.rickandmorty.detail.interactor

import john.snow.rickandmorty.detail.presentation.CharacterDetailsPresenter
import john.snow.rickandmorty.detail.repository.CharacterDetailsRepository
import john.snow.rickandmorty.model.RMCharacter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CharacterDetailsInteractorImplTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var presenter: CharacterDetailsPresenter
    @Mock
    private lateinit var repository: CharacterDetailsRepository

    private lateinit var interactor: CharacterDetailsInteractor

    @Before
    fun setUp() {
        interactor = CharacterDetailsInteractorImpl(repository, presenter)
    }

    @Test
    fun getCharacter_WhenRepositoryThrowException() {
        val exception = CharacterDetailsRepository.CharacterDetailsException()
        given(repository.getCharacter(42)).willThrow(exception)

        interactor.getCharacter(42)

        verify(presenter).presentCharacterError(exception)
    }

    @Test
    fun getCharacter() {
        val character = mock(RMCharacter::class.java)
        given(repository.getCharacter(42)).willReturn(character)

        interactor.getCharacter(42)

        verify(presenter).presentCharacter(character)
    }
}