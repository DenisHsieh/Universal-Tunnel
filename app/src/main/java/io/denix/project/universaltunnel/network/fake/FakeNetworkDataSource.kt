package io.denix.project.universaltunnel.network.fake

import android.content.res.AssetManager
import io.denix.project.universaltunnel.network.NetworkDataSource
import io.denix.project.universaltunnel.network.model.NetworkNote
import io.denix.project.universaltunnel.network.model.NetworkUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class FakeNetworkDataSource(
    private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: AssetManager,
) : NetworkDataSource {

    companion object {
        private const val USER_ASSET = "user.json"
        private const val NOTE_ASSET = "note.json"
    }

    override suspend fun getUsers(): List<NetworkUser> =
        withContext(ioDispatcher) {
            assets.open(USER_ASSET).use(networkJson::decodeFromStream)
        }

    override suspend fun getNotes(): List<NetworkNote> =
        withContext(ioDispatcher) {
            assets.open(NOTE_ASSET).use(networkJson::decodeFromStream)
        }
}