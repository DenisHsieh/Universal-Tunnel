package io.denix.project.universaltunnel.data.user.model

import io.denix.project.universaltunnel.network.model.NetworkUser

fun NetworkUser.asEntity() = UserEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    age = age,
    gender = gender,
    height = height,
    weight = weight
)