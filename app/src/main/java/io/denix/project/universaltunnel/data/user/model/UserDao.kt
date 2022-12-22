package io.denix.project.universaltunnel.data.user.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    /**
     * with Flow
      */
    @Query(value = "SELECT * FROM user_table WHERE id = :userId")
    fun getUserEntityFlow(userId: Int): Flow<UserEntity>

    /**
     * with Flow
     */
    @Query(value = "SELECT * FROM user_table")
    fun getUserEntitiesFlow(): Flow<List<UserEntity>>

    /**
     * with Flow
     */
    @Query(value = "SELECT * FROM user_table WHERE id IN (:userIds)")
    fun getUserEntitiesFlow(userIds: Set<Int>): Flow<List<UserEntity>>

    @Query(value = "SELECT * FROM user_table WHERE id = :userId")
    fun getUserEntity(userId: Int): UserEntity

    @Query(value = "SELECT * FROM user_table")
    fun getUserEntities(): List<UserEntity>

    @Query(value = "SELECT * FROM user_table WHERE id IN (:userIds)")
    fun getUserEntities(userIds: Set<Int>): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreUsers(userEntities: List<UserEntity>): List<Long>

    /**
     * Updates [entities] in the db that match the primary key, and no-ops if they don't
     */
    @Update
    suspend fun updateUsers(entities: List<UserEntity>)

    /**
     * Inserts or updates [entities] in the db under the specified primary keys
     */
    @Upsert
    suspend fun upsertUsers(entities: List<UserEntity>)

    @Query(value = "DELETE FROM user_table WHERE id in (:userIds)")
    suspend fun deleteUsers(userIds: List<Int>)

    @Query(value = "DELETE FROM user_table")
    suspend fun deleteAllUsers()
}