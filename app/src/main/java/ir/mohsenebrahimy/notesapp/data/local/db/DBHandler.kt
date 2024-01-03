package ir.mohsenebrahimy.notesapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.mohsenebrahimy.notesapp.data.local.db.dao.NotesDao
import ir.mohsenebrahimy.notesapp.data.model.NotesEntity

@Database(
    entities = [NotesEntity::class],
    version = DBHandler.DATABASE_VERSION
)
abstract class DBHandler : RoomDatabase(), NotesDao {
    abstract fun noteDao() : NotesDao

    companion object {
        private const val DATABASE_NAME = "note"
        const val DATABASE_VERSION      = 1

        const val NOTES_TABLE           = "notes"
        const val NOTES_ID              = "id"
        const val NOTES_TITLE           = "title"
        const val NOTES_DETAIL          = "detail"
        const val NOTES_DELETE_STATE    = "deleteState"
        const val NOTES_DATE            = "date"

        const val TRUE_STATE            = "1"
        const val FALSE_STATE           = "0"

        private var INSTANCE: DBHandler? = null
        fun getDatabase(context: Context): DBHandler {
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(
                    context,
                    DBHandler::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return INSTANCE!!
        }
    }
}

