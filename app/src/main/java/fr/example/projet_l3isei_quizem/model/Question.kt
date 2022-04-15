package fr.example.projet_l3isei_quizem.model

import java.io.Serializable

class Question (
    var content: String = "Sans titre",
    var multiple: Boolean = false,
    // stocker les reponses pour les choix multiples
    var reponses: ArrayList<String> = ArrayList(),
    var nbReponses: Int = 0
) : Serializable

