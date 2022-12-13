package io.denix.project.universaltunnel.data.user.repository

import io.denix.project.universaltunnel.data.external.User
import io.denix.project.universaltunnel.data.util.Syncable
import kotlinx.coroutines.flow.Flow

interface UserRepository : Syncable {
    /**
     * Gets the available users as a stream
     */
    fun getUsers(): Flow<List<User>>

    /**
     * Gets data for a specific user
     */
    fun getUser(id: Int): Flow<User>
}