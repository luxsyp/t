package john.snow.rickandmorty.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import john.snow.rickandmorty.model.RMCharacterResponsePage

@Dao
abstract class CharacterResponsePageDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(characterResponsePage: RMCharacterResponsePage)

    @Query("SELECT * FROM characterResponsePage")
    abstract fun loadAll(): List<RMCharacterResponsePage>

    @Query("SELECT * FROM characterResponsePage WHERE page = :pageNumber")
    abstract fun loadByPageNumber(pageNumber: Int): RMCharacterResponsePage?
}