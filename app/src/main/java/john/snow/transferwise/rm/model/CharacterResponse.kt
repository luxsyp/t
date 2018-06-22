package john.snow.transferwise.rm.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
        val next: CharacterResponseInfo? = null,
        val results: List<Character>? = null
)

data class CharacterResponseInfo(
        val count: Int = 0,
        val next: String? = null
)

@Entity(tableName = "character")
data class Character(
        @PrimaryKey @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("status") val status: String,
        @SerializedName("image") val image: String
)