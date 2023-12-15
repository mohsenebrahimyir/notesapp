package ir.mohsenebrahimy.notesapp.db.model

data class DBNotesModel (
    var id          : Int,
    var title       : String,
    var detail      : String,
    var deleteState : String,
    var date        : String
)
