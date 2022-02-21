package nl.romano.kleeren.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.util.* // ktlint-disable no-wildcard-imports

@Entity(tableName = "user_search")
data class UserSearch(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = "search_term")
    val searchTerm: String,

    @ColumnInfo(name = "entry_date")
    val timestamp: Date = Date.from(Instant.now())
)
