package john.snow.rickandmorty.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import john.snow.rickandmorty.model.RMCharacter
import john.snow.rickandmorty.model.RMCharacterResponsePage

@Database(
        entities = [
            RMCharacter::class,
            RMCharacterResponsePage::class
        ],
        version = 1,
        exportSchema = false
)
@TypeConverters(IntegerListConverter::class, StringListConverter::class)
abstract class CharacterDb : RoomDatabase() {
    abstract fun characterDAO(): CharacterDAO
    abstract fun characterResponsePageDAO(): CharacterResponsePageDAO

    companion object {
        const val DB_NAME = "r&m.db"
    }
}
