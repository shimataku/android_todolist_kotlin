package com.freecanvas.todoapp.entity


data class TodoArrayJson(
    val count: Int?,
    val data: List<TodoJson?>?
)

data class TodoJson(
    val id: String?,
    val user: String?,
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

    fun toMap() : Map<String, Any?> {
        val map = mapOf("id" to id,
                "user" to user,
                "title" to title,
                "description" to description,
                "publishedDate" to publishedDate,
                "startDate" to startDate,
                "limitDate" to limitDate,
                "isFix" to isFix)
        return map
    }
}
