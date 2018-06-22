package john.snow.transferwise.rm.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import john.snow.transferwise.rm.model.Character

@Database(
        entities = [
            Character::class
        ],
        version = 1,
        exportSchema = false
)
abstract class CharacterDb : RoomDatabase() {
    abstract fun characterDAO(): CharacterDAO
}
