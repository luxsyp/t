package john.snow.rickandmorty.detail.repository

import john.snow.rickandmorty.api.RMDetailService
import john.snow.rickandmorty.model.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import retrofit2.Response

class CharacterDetailsRepositoryImplTest {

    @get:Rule
    var mockito: MethodRule = MockitoJUnit.rule()

    @Mock
    private lateinit var service: RMDetailService

    private lateinit var repository: CharacterDetailsRepository

    @Before
    fun setUp() {
        repository = CharacterDetailsRepositoryImpl(service)
    }

    @Test(expected = CharacterDetailsRepository.CharacterDetailsException::class)
    fun testGetCharacter_WhenApiReturnsError() {
        val response = mockGeneric<ApiErrorResponse<RMCharacter>>()
        given(service.getCharacter(21)).willReturn(response)

        repository.getCharacter(21)
    }

    @Test(expected = CharacterDetailsRepository.CharacterDetailsException::class)
    fun testGetCharacter_WhenApiReturnsEmpty() {
        val response = mockGeneric<ApiEmptyResponse<RMCharacter>>()
        given(service.getCharacter(21)).willReturn(response)

        repository.getCharacter(21)
    }

    @Test
    fun testGetCharacter() {
        val response = ApiResponse.create(Response.success(RMCharacter(
                1,
                "name",
                "status",
                "https://image.jpg",
                listOf("Episode1", "Episode2")
        )))
        given(service.getCharacter(21)).willReturn(response)

        val result = repository.getCharacter(21)

        assertThat(result).isNotNull
        assertThat(result).isEqualTo(RMCharacter(
                1,
                "name",
                "status",
                "https://image.jpg",
                listOf("Episode1", "Episode2")
        ))
    }

    private inline fun <reified T : Any> mockGeneric(): T = Mockito.mock(T::class.java) as T
}