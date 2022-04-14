package fr.example.projet_l3isei_quizem.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import fr.example.projet_l3isei_quizem.MainActivity

import fr.example.projet_l3isei_quizem.R
import fr.example.projet_l3isei_quizem.adapter.CollectionAdapter
import fr.example.projet_l3isei_quizem.SurveyRepository

class CollectionFragment(

   //private val context:MainActivity

) : Fragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_collection, container, false)

        // Récupère la liste à injecter dans fragment_collection
        val surveyList = SurveyRepository.Singleton.surveyList

        for (i in 0..surveyList.size-1) {

            // numérotation du questionnaire
            surveyList.get(i).title = (i+1).toString()+". ${surveyList.get(i).title}"

            // si le questionnaire contient plus d'une question, on affiche les 2 premieres questions
            if (surveyList.get(i).questions.size>1) {
                surveyList.get(i).description = "1. "+surveyList.get(i).questions.get(0).content+"\n"+"2. "+surveyList.get(i).questions.get(1).content
            }

            // si le questionnaire ne contient qu'une question
            if (surveyList.get(i).questions.size==1)
                surveyList.get(i).description = "1. "+surveyList.get(i).questions.get(0).content+"\n"

        }

        // récupérer RecycleView collection
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycle_view)

        //collectionRecyclerView.isNestedScrollingEnabled = false

        val context =  requireContext()


        // Appel adapter
        collectionRecyclerView.adapter = CollectionAdapter(surveyList,context)

/*
        val convertRecyclerView = view.findViewById<RecyclerView>(R.id.convert_recycle_view)
        convertRecyclerView.adapter = ConvertAdapter(context)
*/
        return view
    }
}