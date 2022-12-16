package io.denix.project.universaltunnel.data.login

import androidx.room.*

@Dao
interface LoginDao {

    @Query(value = "SELECT * FROM login_table")
    fun getLoginEntities(): List<LoginEntity>

    @Query(value = "SELECT * FROM login_table WHERE userId = :userId")
    fun getLoginEntities(userId: Int): List<LoginEntity>

    @Insert
    suspend fun insertLogin(entity: LoginEntity)

    @Query(value = "DELETE FROM login_table")
    suspend fun deleteAllLogins()
}