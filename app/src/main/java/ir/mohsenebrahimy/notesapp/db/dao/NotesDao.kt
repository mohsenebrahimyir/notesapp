package ir.mohsenebrahimy.notesapp.db.dao

import android.content.ContentValues
import ir.mohsenebrahimy.notesapp.db.DBHelper
import ir.mohsenebrahimy.notesapp.db.model.DBNotesModel

class NotesDao (
    private val db: DBHelper
) {
    private val contentValues = ContentValues()

    fun saveNotes(notes : DBNotesModel) : Boolean {
        val database = db.writableDatabase
        setContentValues(notes)
        val result = database.insert(
            DBHelper.NOTES_TABLE,
            null,
            contentValues
        )
        database.close()
        return result > 0
    }

    private fun setContentValues(notes: DBNotesModel) {
        contentValues.clear()
        contentValues.put(DBHelper.NOTES_TITLE, notes.title)
        contentValues.put(DBHelper.NOTES_DETAIL, notes.detail)
        contentValues.put(DBHelper.NOTES_DELETE_STATE, notes.deleteState)
        contentValues.put(DBHelper.NOTES_DATE, notes.date)
    }
}