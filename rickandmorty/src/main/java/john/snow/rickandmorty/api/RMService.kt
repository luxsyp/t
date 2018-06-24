package john.snow.rickandmorty.api

import john.snow.rickandmorty.model.ApiResponse
import john.snow.rickandmorty.model.RMCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RMService {

    @GET("character")
    fun getCharacters(): ApiResponse<RMCharacterResponse>

    @GET("character")
    fun getCharacters(@Query("page") page: Int): ApiResponse<RMCharacterResponse>
}