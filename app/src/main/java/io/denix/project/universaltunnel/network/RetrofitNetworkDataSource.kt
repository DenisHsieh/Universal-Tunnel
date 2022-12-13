package io.denix.project.universaltunnel.network

import io.denix.project.universaltunnel.network.model.NetworkNote
import io.denix.project.universaltunnel.network.model.NetworkUser

class RetrofitNetworkDataSource : NetworkDataSource{
    override suspend fun getUsers(): List<NetworkUser> {
        TODO("Not yet implemented")
    }

    override suspend fun getNotes(): List<NetworkNote> {
        TODO("Not yet implemented")
    }
}