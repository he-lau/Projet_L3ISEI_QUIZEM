package fr.example.projet_l3isei_quizem.model

import java.io.Serializable

class Question (
    var content: String = "Sans titre",
    var multiple: Boolean = false,
    var reponses: ArrayList<String> = ArrayList<String>(),
    var nbReponses: Int = 0
) : Serializable

