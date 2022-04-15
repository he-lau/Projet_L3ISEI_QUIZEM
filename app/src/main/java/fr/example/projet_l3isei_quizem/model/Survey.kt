package fr.example.projet_l3isei_quizem.model

import java.io.Serializable

class Survey (
    val id:String = "survey0",
    var title: String = "Sans titre",
    // description sera determinée par les questions
    var description: String = "",
    var questions: ArrayList<Question> = ArrayList()
) : Serializable

