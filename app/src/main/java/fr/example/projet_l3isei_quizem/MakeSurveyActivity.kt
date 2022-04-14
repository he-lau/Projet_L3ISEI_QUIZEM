package fr.example.projet_l3isei_quizem

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import fr.example.projet_l3isei_quizem.adapter.MakeSurveyAdapter
import fr.example.projet_l3isei_quizem.model.Question
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.*
import androidx.core.app.ActivityCompat
import fr.example.projet_l3isei_quizem.model.Survey


class MakeSurveyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // test
        //val questionnaireIntent = intent.getSerializableExtra("SURVEY") as? Survey

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_survey)
        //setContentView(R.layout.item_make_survey)

        // Toolbar avec retour activity parent
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setTitle("")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        // vues
        val checkBox = findViewById<CheckBox>(R.id.multiple_choice)
        val addQuestion = findViewById<ImageButton>(R.id.make_survey_add)
        val confirm = findViewById<TextView>(R.id.make_survey_confirm)
        val surveyTitle = findViewById<EditText>(R.id.make_survey_title)


        // init data
        val questionsList = ArrayList<Question>()
        var checkedState : ArrayList<Boolean> = ArrayList(questionsList.size)

        checkBox.setOnCheckedChangeListener(null)
        // intance adapter
        val adapter = MakeSurveyAdapter(questionsList, this, checkBox, checkedState, confirm, surveyTitle)
        //checkBox.setOnCheckedChangeListener(null)
        // Ecouteurs

        addQuestion.setOnClickListener {

            if(checkBox.isChecked) {
                questionsList.add(Question(multiple = true, reponses = ArrayList<String>()))
                checkedState.add(true)
            }
            else {
                questionsList.add(Question(multiple = false, reponses = ArrayList<String>()))
                checkedState.add(false)
            }

            adapter.notifyDataSetChanged()
        }



        // branchement adapter au RecyclerView
        val viewQuestions = findViewById<RecyclerView>(R.id.make_survey_recycle_view)

        viewQuestions.adapter = adapter

    }

}