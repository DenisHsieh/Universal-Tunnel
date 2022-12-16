package io.denix.project.universaltunnel.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.denix.project.universaltunnel.data.login.LoginDao
import io.denix.project.universaltunnel.data.login.LoginEntity
import io.denix.project.universaltunnel.data.note.model.NoteDao
import io.denix.project.universaltunnel.data.note.model.NoteEntity
import io.denix.project.universaltunnel.data.user.model.UserDao
import io.denix.project.universaltunnel.data.user.model.UserEntity
import kotlinx.coroutines.CoroutineScope

@Database(entities = [UserEntity::class, LoginEntity::class, NoteEntity::class], version = 1, exportSchema = false)
abstract class UtRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun loginDao(): LoginDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: UtRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): UtRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UtRoomDatabase::class.java,
                    "ut_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}