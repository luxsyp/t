package john.snow.transferwise.rm.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import john.snow.transferwise.rm.model.Character

@Dao
abstract class CharacterDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(characters: List<Character>)

    @Query("SELECT * FROM character")
    abstract fun loadAll(): LiveData<List<Character>>

    @Query("SELECT * FROM character WHERE id = :id")
    abstract fun load(id: Int): Character

    @Query("SELECT * FROM character WHERE id in (:ids)")
    abstract fun load(ids: List<Int>): List<Character>
}