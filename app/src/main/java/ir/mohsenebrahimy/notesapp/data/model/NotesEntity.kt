package ir.mohsenebrahimy.notesapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.mohsenebrahimy.notesapp.data.local.db.DBHandler

@Entity(tableName = DBHandler.NOTES_TABLE)
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)          val id: Int = 0,
    @ColumnInfo(DBHandler.NOTES_TITLE)        val title: String,
    @ColumnInfo(DBHandler.NOTES_DETAIL)       var detail: String,
    @ColumnInfo(DBHandler.NOTES_DELETE_STATE) var deleteState: String,
    @ColumnInfo(DBHandler.NOTES_DATE)         var date: String
)
