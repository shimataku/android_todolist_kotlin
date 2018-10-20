package com.freecanvas.todoapp.entity


data class TodoArrayJson(
    val count: Int?,
    val data: List<TodoJson?>?
)

data class TodoJson(
    val id: String?,
    val title: String?,
    val description: String?,
    val publishedDate: Long?,
    val startDate: Long?,
    val limitDate: Long?,
    val isFix: Boolean?
){
    fun toJson() : String {
        val jsonStr = String.format(
                "{ \"title\" : \"%s\"," +
                " \"description\" : \"%s\" ," +
                " \"startDate\" : \"%d\" ," +
                " \"endDate\" : \"%d\"}", title, description, startDate, limitDate)
        return jsonStr
    }
}
