package com.freecanvas.todoapp.entity


data class TodoJson(
    val count: Int?,
    val data: List<Data?>?
)

data class Data(
    val id: String?,
    val title: String?,
    val description: String?,
    val publishedDate: Long?,
    val startDate: Long?,
    val limitDate: Long?,
    val isFix: Boolean?
)
