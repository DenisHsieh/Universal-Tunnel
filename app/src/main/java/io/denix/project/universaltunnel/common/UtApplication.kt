package io.denix.project.universaltunnel.common

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UtApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { UtRoomDatabase.getDatabase(this, applicationScope) }
//    val userRepository by lazy { OfflineUserRepository(database.userDao()) }
}