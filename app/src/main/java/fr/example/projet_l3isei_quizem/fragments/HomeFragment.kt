package fr.example.projet_l3isei_quizem.fragments

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import fr.example.projet_l3isei_quizem.MainActivity
import fr.example.projet_l3isei_quizem.MakeSurveyActivity
import fr.example.projet_l3isei_quizem.R


public class HomeFragment(

    //private val context: MainActivity

) : Fragment() {

    //constructor() : this(MainActivity())

    /*
    *   LayoutInflater : creer objet View (ou Layout) à partir du XML
    *
    *   public View onCreateView (LayoutInflater inflater,
                                  ViewGroup container,
                                  Bundle savedInstanceState)
    */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        val homeButton = view.findViewById<Button>(R.id.button_home)

        homeButton.setOnClickListener {
            openMakeSurveyActivity()
        }

        return view
    }

    fun openMakeSurveyActivity() {
        val intent = Intent(context, MakeSurveyActivity::class.java)
        startActivity(intent)
    }
}