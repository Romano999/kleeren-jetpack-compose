package nl.romano.kleeren.util

import androidx.room.TypeConverter
import java.util.* // ktlint-disable no-wildcard-imports

class UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID): String? {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?): UUID? {
        return UUID.fromString(string)
    }
}
