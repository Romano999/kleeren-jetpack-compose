package nl.romano.kleeren.util

import androidx.room.TypeConverter
import java.util.* // ktlint-disable no-wildcard-imports

class DateConverter {
    @TypeConverter
    fun timeStampFromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun dateFromTimestamp(timestamp: Long): Date? {
        return Date(timestamp)
    }
}
