package io.denix.project.universaltunnel.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.denix.project.universaltunnel.data.note.model.NoteDao
import io.denix.project.universaltunnel.data.note.model.NoteEntity
import io.denix.project.universaltunnel.data.user.model.UserDao
import io.denix.project.universaltunnel.data.user.model.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [UserEntity::class, NoteEntity::class], version = 1, exportSchema = false)
abstract class UtRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
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
                ).addCallback(UTDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class UTDatabaseCallback(private val scope: CoroutineScope): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateUser(database.userDao())
                }
            }
        }

        fun populateUser(userDao: UserDao) {
            // userDao.deleteAllUsers()
        }
    }
}