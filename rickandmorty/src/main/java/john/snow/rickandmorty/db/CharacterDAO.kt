package john.snow.rickandmorty.db
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import john.snow.rickandmorty.model.RMCharacter

@Dao
abstract class CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(characters: List<RMCharacter>)

    @Query("SELECT * FROM character")
    abstract fun loadAll(): List<RMCharacter>

    @Query("SELECT * FROM character WHERE id = :id")
    abstract fun load(id: Int): RMCharacter

    @Query("SELECT * FROM character WHERE id in (:ids)")
    abstract fun load(ids: List<Int>): List<RMCharacter>
}