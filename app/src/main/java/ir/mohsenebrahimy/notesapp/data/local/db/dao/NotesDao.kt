package ir.mohsenebrahimy.notesapp.data.local.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import ir.mohsenebrahimy.notesapp.data.model.RecyclerNotesModel
import ir.mohsenebrahimy.notesapp.data.local.db.DBHelper
import ir.mohsenebrahimy.notesapp.data.model.DBNotesModel
import java.lang.Exception

class NotesDao(
    private val db: DBHelper
) {
    private lateinit var cursor: Cursor
    private val contentValues = ContentValues()
    fun saveNotes(notes: DBNotesModel): Boolean {
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

    fun getNotesForRecycler(value: String): ArrayList<RecyclerNotesModel> {
        val database = db.readableDatabase
        val query =
            "SELECT ${DBHelper.NOTES_ID}, ${DBHelper.NOTES_TITLE} " +
            "FROM ${DBHelper.NOTES_TABLE} " +
            "WHERE ${DBHelper.NOTES_DELETE_STATE} = ?"
        cursor = database.rawQuery(query, arrayOf(value))
        val data = getDataForRecycler()
        cursor.close()
        database.close()
        return data
    }

    private fun getDataForRecycler(): ArrayList<RecyclerNotesModel> {
        val data = ArrayList<RecyclerNotesModel>()

        try {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(getIndex(DBHelper.NOTES_ID))
                    val title = cursor.getString(getIndex(DBHelper.NOTES_TITLE))
                    data.add(RecyclerNotesModel(id, title))
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }

        return data
    }

    private fun getIndex(name: String) = cursor.getColumnIndex(name)
}