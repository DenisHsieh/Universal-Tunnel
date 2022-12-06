package io.denix.project.universaltunnel.data.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg user: User)

    @Delete
    fun delete(vararg user: User)

    @Query("DELETE FROM user_table")
    fun deleteAll()

    @Update
    fun updateUser(vararg user: User)

    @Query("SELECT * FROM user_table ORDER BY firstName ASC")
    fun getAlphabetizedUsers(): Flow<List<User>>
}