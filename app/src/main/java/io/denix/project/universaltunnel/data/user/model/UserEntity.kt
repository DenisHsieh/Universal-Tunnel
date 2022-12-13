package io.denix.project.universaltunnel.data.user.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.denix.project.universaltunnel.data.external.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "firstName") val firstName: String?,
    @ColumnInfo(name = "lastName") val lastName: String?,
    @ColumnInfo(name = "age") val age: Int?,
    @ColumnInfo(name = "gender") val gender: String?,
    @ColumnInfo(name = "height") val height: Int?,
    @ColumnInfo(name = "weight") val weight: Int?
)

fun UserEntity.asExternalModel() = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    age = age,
    gender = gender,
    height = height,
    weight = weight
)
