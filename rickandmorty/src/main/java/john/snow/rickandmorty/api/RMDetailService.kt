package john.snow.rickandmorty.api

import john.snow.rickandmorty.model.ApiResponse
import john.snow.rickandmorty.model.RMCharacter
import retrofit2.http.GET
import retrofit2.http.Path

interface RMDetailService {

    @GET("character/{id}")
    fun getCharacter(@Path("id") characterId: Int): ApiResponse<RMCharacter>
}