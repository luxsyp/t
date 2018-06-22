package john.snow.rickandmorty.model


data class RMCharacterResponse(
        val info: RMCharacterResponseInfo?,
        val results: List<RMCharacter>?
)

