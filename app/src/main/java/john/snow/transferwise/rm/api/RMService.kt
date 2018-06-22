package john.snow.transferwise.rm.api

import john.snow.transferwise.rm.model.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RMService {

    @GET("character")
    fun getCharacters(): ApiResponse<CharacterResponse>

    @GET("character")
    fun getCharacters(@Url url: String): Call<CharacterResponse>

}