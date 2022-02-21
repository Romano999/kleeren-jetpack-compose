package nl.romano.kleeren.repository

import kotlinx.coroutines.flow.Flow
import nl.romano.kleeren.data.UserSearchDatabaseDao
import nl.romano.kleeren.model.UserSearch
import javax.inject.Inject

class UserSearchRepository @Inject constructor(private val userSearchDatabaseDao: UserSearchDatabaseDao) {
    fun getUserSearches(): Flow<List<UserSearch>> = userSearchDatabaseDao.getUserSearches()
    suspend fun getUserSearchById(id: String): UserSearch = userSearchDatabaseDao.getUserSearchById(id)
    suspend fun insert(userSearch: UserSearch) = userSearchDatabaseDao.insert(userSearch)
    suspend fun deleteAll() = userSearchDatabaseDao.deleteAll()
    suspend fun deleteNote(userSearch: UserSearch) = userSearchDatabaseDao.deleteNote(userSearch)
}
