package nl.romano.kleeren.data

import androidx.room.* // ktlint-disable no-wildcard-imports
import kotlinx.coroutines.flow.Flow
import nl.romano.kleeren.model.UserSearch

@Dao
interface UserSearchDatabaseDao {
    @Query("SELECT * FROM user_search")
    fun getUserSearches(): Flow<List<UserSearch>>

    @Query("SELECT * FROM user_search WHERE id = :id")
    suspend fun getUserSearchById(id: String): UserSearch

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userSearch: UserSearch)

    @Query("DELETE FROM user_search")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(userSearch: UserSearch)
}
