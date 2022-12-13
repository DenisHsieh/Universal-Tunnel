package io.denix.project.universaltunnel.network

import io.denix.project.universaltunnel.network.model.NetworkNote
import io.denix.project.universaltunnel.network.model.NetworkUser

interface NetworkDataSource {
    suspend fun getUsers(): List<NetworkUser>

//    suspend fun getHealths(): List<NetworkHealth>
//
//    suspend fun getFinances(): List<NetworkFinance>
//
    suspend fun getNotes(): List<NetworkNote>
//
//    suspend fun getMedias(): List<NetworkMedia>
}