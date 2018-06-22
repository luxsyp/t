package john.snow.rickandmorty.api

import john.snow.rickandmorty.model.ApiResponse
import john.snow.rickandmorty.model.RMCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface RMService {
    @GET("character")
    fun getCharacters(): ApiResponse<RMCharacterResponse>

    @GET("character")
    fun getCharacters(@Url url: String): ApiResponse<RMCharacterResponse>
}