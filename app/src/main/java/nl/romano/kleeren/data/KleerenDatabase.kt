package nl.romano.kleeren.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nl.romano.kleeren.model.UserSearch
import nl.romano.kleeren.util.DateConverter
import nl.romano.kleeren.util.UUIDConverter

@Database(entities = [UserSearch::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class KleerenDatabase : RoomDatabase() {
    abstract fun userSearchDao(): UserSearchDatabaseDao
}
