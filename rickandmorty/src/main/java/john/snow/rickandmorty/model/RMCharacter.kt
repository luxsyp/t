package john.snow.rickandmorty.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "character")
data class RMCharacter(
        @PrimaryKey @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("status") val status: String,
        @SerializedName("image") val image: String
)