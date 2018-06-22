package john.snow.rickandmorty.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class IntegerListConverter {

    @TypeConverter
    fun fromString(value: String): List<Int>? {
        val listType = object : TypeToken<List<Int>>() {
        }.type
        return Gson().fromJson<List<Int>>(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Int>): String {
        return Gson().toJson(list)
    }
}