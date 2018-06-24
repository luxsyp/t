package john.snow.rickandmorty.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "characterResponsePage")
data class RMCharacterResponsePage(
        @PrimaryKey val page: Int,
        val next: String? = null,
        val repoIds: List<Int>,
        val count: Int
)
