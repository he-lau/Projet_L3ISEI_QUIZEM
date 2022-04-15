package fr.example.projet_l3isei_quizem.adapter

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.example.projet_l3isei_quizem.MakeSurveyActivity
import fr.example.projet_l3isei_quizem.R
import fr.example.projet_l3isei_quizem.SurveyRepository
import fr.example.projet_l3isei_quizem.model.Question
import fr.example.projet_l3isei_quizem.model.Survey


class MakeSurveyAdapter(

    // liste des questions à afficher à l'écran
    private var questionsList: ArrayList<Question>,
    val context: MakeSurveyActivity,
    private val checkBox : CheckBox,
    val confirm:TextView,
    val surveyTitle:EditText
    //private var itemStateArray:SparseBooleanArray = SparseBooleanArray(questionsList.size)




) : RecyclerView.Adapter<MakeSurveyAdapter.ViewHolder>(){


    /*
    *
    *   Récupère tout les composants à contrôler/ manipuler
    *
    */
    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {

        val makeSurveyItem = view.findViewById<CardView>(R.id.make_survey_item)
        val numQuestion = view.findViewById<TextView>(R.id.num_question)
        val questionTitle = view.findViewById<EditText>(R.id.make_survey_question_title)
        val questionDelete = view.findViewById<ImageView>(R.id.question_delete)
        val questionCounter = view.findViewById<TextView>(R.id.question_answer_counter)
        val questionCounterLess = view.findViewById<ImageView>(R.id.question_answer_counter_less)
        val questionCounterAdd = view.findViewById<ImageView>(R.id.question_answer_counter_add)

    }

    // définir la vue
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //TODO("charger l'item layout pour les choix multiple"), utiliser viewType !

            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_make_survey, parent, false)
            return ViewHolder(view)
    }
    /*
    *
    *   Maj item
    *
    */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //holder.setIsRecyclable(false)

        // element courant avec position
        val currentQuestion = questionsList[position]


        Log.d("INPUT checkBox", checkBox.isChecked.toString())
        Log.d("COURANT no ${position+1}", currentQuestion.multiple.toString())

        holder.numQuestion.text = "N°"+(position+1).toString()

        if (currentQuestion.multiple) {
            holder.numQuestion.setBackgroundColor(Color.RED)
        }

        holder.questionTitle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentQuestion.content = s.toString()
            }
        })

        holder.questionDelete.setOnClickListener {
            questionsList.remove(currentQuestion)
            //notifyDataSetChanged()
            notifyItemRemoved(position)
        }

        holder.questionCounterAdd.setOnClickListener{
            currentQuestion.nbReponses+=1
            //notifyDataSetChanged()
            notifyItemChanged(position)
        }
        holder.questionCounterLess.setOnClickListener{
            if (currentQuestion.nbReponses>0) {
                currentQuestion.nbReponses-=1
            }

            //notifyDataSetChanged()
            notifyItemChanged(position)
        }

        holder.questionCounter.text = "Nombre de ligne de réponse : "+currentQuestion.nbReponses.toString()


        val repo = SurveyRepository()

        val surveyList = SurveyRepository.Singleton.surveyList
        val idCurrentSurvey = "survey"+surveyList.size

        // confirm : injection à la db
        confirm.setOnClickListener {

            // la description sera déterminée dans CollectionFragment
            repo.writeNewSurvey(Survey(idCurrentSurvey,surveyTitle.text.toString(),"",questionsList))

        }

    }



    /*
    *   Nombre d'item à afficher
    *
    */
    override fun getItemCount(): Int = questionsList.size

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}