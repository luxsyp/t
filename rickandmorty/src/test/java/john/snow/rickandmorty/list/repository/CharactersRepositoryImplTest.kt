package john.snow.rickandmorty.list.repository

import john.snow.rickandmorty.api.RMListService
import john.snow.rickandmorty.model.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.MethodRule
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import retrofit2.Response

class CharactersRepositoryImplTest {

    @get:Rule
    var mockito: MethodRule = MockitoJUnit.rule()

    @Mock
    private lateinit var listService: RMListService

    private lateinit var repository: CharactersRepository

    @Before
    fun setUp() {
        repository = CharactersRepositoryImpl(listService)
    }

    @Test(expected = CharactersRepository.CharactersException::class)
    fun testGetCharacters_WhenApiReturnsError() {
        val response = mockGeneric<ApiErrorResponse<RMCharacterResponse>>()
        given(listService.getCharacters()).willReturn(response)

        repository.getCharacters()
    }

    @Test
    fun testGetCharacters_WhenApiReturnsEmptyResponse() {
        val response = mockGeneric<ApiEmptyResponse<RMCharacterResponse>>()
        given(listService.getCharacters()).willReturn(response)

        val result = repository.getCharacters()

        assertThat(result).isEmpty()
    }

    @Test(expected = CharactersRepository.CharactersException::class)
    fun testGetCharacters_WhenApiReturnsResponseWithEmptyResults() {
        val response = ApiResponse.create(Response.success(RMCharacterResponse(
                RMCharacterResponseInfo(
                        count = 0,
                        next = ""),
                null
        )))
        given(listService.getCharacters()).willReturn(response)

        repository.getCharacters()
    }

    @Test
    fun testGetCharacters() {
        val response = ApiResponse.create(Response.success(RMCharacterResponse(
                RMCharacterResponseInfo(
                        count = 2,
                        next = "domain.com?page=2"),
                listOf(mock(RMCharacter::class.java), mock(RMCharacter::class.java))
        )))
        given(listService.getCharacters()).willReturn(response)

        val result = repository.getCharacters()

        assertThat(result).isNotEmpty
        assertThat(result.size).isEqualTo(2)
    }

    @Test(expected = CharactersRepository.CharactersException::class)
    fun testGetCharactersNextPage_WhenApiReturnsError() {
        testGetCharacters()
        val response = mockGeneric<ApiErrorResponse<RMCharacterResponse>>()
        given(listService.getCharacters(2)).willReturn(response)

        repository.getCharactersNextPage()
    }

    @Test
    fun testGetCharactersNextPage_WhenApiReturnsEmptyResponse() {
        testGetCharacters()
        val response = mockGeneric<ApiEmptyResponse<RMCharacterResponse>>()
        given(listService.getCharacters(2)).willReturn(response)

        val result = repository.getCharactersNextPage()

        assertThat(result).isEmpty()
    }

    @Test(expected = CharactersRepository.CharactersException::class)
    fun testGetCharactersNextPage_WhenApiReturnsResponseWithEmptyResults() {
        testGetCharacters()
        val response = ApiResponse.create(Response.success(RMCharacterResponse(
                RMCharacterResponseInfo(
                        count = 0,
                        next = ""),
                null
        )))
        given(listService.getCharacters(2)).willReturn(response)

        repository.getCharactersNextPage()
    }

    @Test
    fun testGetCharactersNextPage() {
        testGetCharacters()
        val response = ApiResponse.create(Response.success(RMCharacterResponse(
                RMCharacterResponseInfo(
                        count = 2,
                        next = "domain.com?page=3"),
                listOf(mock(RMCharacter::class.java), mock(RMCharacter::class.java), mock(RMCharacter::class.java))
        )))
        given(listService.getCharacters(2)).willReturn(response)

        val result = repository.getCharactersNextPage()

        assertThat(result).isNotEmpty
        assertThat(result.size).isEqualTo(3)
    }

    private inline fun <reified T : Any> mockGeneric(): T = Mockito.mock(T::class.java) as T
}