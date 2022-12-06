package io.denix.project.universaltunnel

import android.app.Application
import io.denix.project.universaltunnel.data.user.UserRepository
import io.denix.project.universaltunnel.util.UTRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class UTApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { UTRoomDatabase.getDatabase(this, applicationScope) }
    val userRepository by lazy { UserRepository(database.userDao()) }
}