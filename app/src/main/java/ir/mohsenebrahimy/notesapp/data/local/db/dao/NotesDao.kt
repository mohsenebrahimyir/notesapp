package ir.mohsenebrahimy.notesapp.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.mohsenebrahimy.notesapp.data.local.db.DBHandler
import ir.mohsenebrahimy.notesapp.data.model.NotesEntity
import ir.mohsenebrahimy.notesapp.data.model.RecyclerNotesModel
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {
    @Insert
    fun saveNotes(vararg notes: NotesEntity): Boolean {
        return true
    }

    @Query(
        "SELECT * " +
                "FROM ${DBHandler.NOTES_TABLE} " +
                "WHERE ${DBHandler.NOTES_ID} = :id"
    )
    suspend fun getNoteById(id: Int): NotesEntity

    @Query(
        "SELECT COUNT(${DBHandler.NOTES_ID}) " +
                "FROM ${DBHandler.NOTES_TABLE} " +
                "WHERE ${DBHandler.NOTES_DELETE_STATE} = :deleteState"
    )
    fun countItems(deleteState: String): Int

    @Query(
        "SELECT ${DBHandler.NOTES_ID}, ${DBHandler.NOTES_TITLE} " +
                "FROM ${DBHandler.NOTES_TABLE} " +
                "WHERE ${DBHandler.NOTES_DELETE_STATE} = :deleteState"
    )
    fun getNotesForRecycler(deleteState: String): ArrayList<RecyclerNotesModel>

    @Update
    fun editNotes(vararg notes: NotesEntity): Boolean {
        return true
    }

    @Delete
    fun deleteNotes(vararg notes: NotesEntity): Boolean {
        return true
    }

    @Query("DELETE FROM ${DBHandler.NOTES_TABLE}")
    fun deleteAllNotes()
}
