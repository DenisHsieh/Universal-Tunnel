package io.denix.project.universaltunnel.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.denix.project.universaltunnel.data.note.Note
import io.denix.project.universaltunnel.data.user.User
import io.denix.project.universaltunnel.data.user.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [User::class, Note::class], version = 1, exportSchema = false)
abstract class UTRoomDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
//    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: UTRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): UTRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UTRoomDatabase::class.java,
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
            userDao.deleteAll()
            //
        }
    }
}