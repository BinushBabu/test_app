package com.example.testbase.data


import com.example.testbase.R


class StaticData() {
    companion object {

        fun getToDoList(): ArrayList<Todo> {

            val list = ArrayList<Todo>()

            val taskList1 = ArrayList<Task>()
            taskList1.add(Task(false,"Take medicine on time","#ffffff"))
            taskList1.add(Task(true,"Wash yesterday cloths","#ffffff"))
            list.add(Todo("04 April 2020","In the morning","#06C5a9","#ffffff",taskList1))

            val taskList2 = ArrayList<Task>()
            taskList2.add(Task(false,"Go to the bank","#000000"))
            taskList2.add(Task(true,"regular in the wave release a work","#000000"))
            taskList2.add(Task(false,"See a movie","#000000"))
            list.add(Todo("04 April 2020","After work","#ffffff","#000000",taskList2))

            val taskList3 = ArrayList<Task>()
            taskList3.add(Task(false,"Call mom","#ffffff"))
            taskList3.add(Task(true,"Read a design journal","#ffffff"))
            list.add(Todo("04 April 2020","Going to bed","#6666ff","#ffffff",taskList3))

            return list
        }

    }
}

data class Todo(
    val date: String,
    val categories : String,
    val color_bg:String,
    val color_text:String,
    val taskList:ArrayList<Task>
)

data class Task(
    val status: Boolean,
    val taskName : String,
    val color_bg:String
)
