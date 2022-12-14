package io.denix.project.universaltunnel.data.util

interface Syncable {
    suspend fun syncInDatabase()
}