package io.denix.project.universaltunnel.data.login

import androidx.room.*

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg login: Login)

    @Query("DELETE FROM login_table")
    fun deleteAll()

    @Query("SELECT loginStatus FROM login_table WHERE userId == :userId")
    fun getLoginStatus(userId: Int): Boolean?

    @Query("SELECT * FROM login_table ORDER BY id DESC LIMIT 1")
    fun getLoginData(): Login
}