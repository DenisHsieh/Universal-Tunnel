package io.denix.project.universaltunnel.data.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_table")
data class Login(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "userId") val userId: Int?,
    @ColumnInfo(name = "loginStatus") val loginStatus: Boolean?
)
