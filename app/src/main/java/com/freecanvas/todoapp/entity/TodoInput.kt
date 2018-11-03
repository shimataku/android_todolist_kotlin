package com.freecanvas.todoapp.entity

data class TodoInput (
        val title: String?,
        val description:String?,
        val publishedDate:Long?,
        val startDate:Long?,
        val limitDate:Long?,
        val isFix:Boolean?
)

