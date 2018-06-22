package john.snow.rickandmorty.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "characterResponsePage")
data class RMCharacterResponsePage(
        @PrimaryKey val next: String,
        val repoIds: List<Int>,
        val count: Int
)
