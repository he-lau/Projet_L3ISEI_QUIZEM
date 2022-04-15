package fr.example.projet_l3isei_quizem.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.example.projet_l3isei_quizem.*
import fr.example.projet_l3isei_quizem.model.Survey


/*
*   adapter : permet de passé du model (DB, Array) à des composants UI
*   ici les données seront fournies à RecycleView
 */
class CollectionAdapter(

    private var surveyList: List<Survey>,
    val context: Context,



) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>(){

    var selectedConvert: Int = -1



    /*
    *
    *   Récupère tout les composants à contrôler/ manipuler
    *
    */
    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        // CardView : item
        val collectionItem = view.findViewById<CardView>(R.id.collection_item)
        // TextView : titre questionnaire
        val surveyName = view.findViewById<TextView>(R.id.item_title)
        // TextView : description du questionnaire (preview)
        val surveyDescription = view.findViewById<TextView>(R.id.item_description)
        // Button : bouton détails questionnaire
        val surveyDetails = view.findViewById<Button>(R.id.button_details)

        val buttonConvert = view.findViewById<ImageButton>(R.id.button_convert)
    }

    // définir la vue
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater
                   .from(parent.context)
                   .inflate(R.layout.item_collection, parent, false)

        return ViewHolder(view)
    }
    /*
    *
    *   Maj item (ici chaque questionnaire de surveyList)
    *
    */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Popup
        val popup = Dialog(context, R.style.WideDialog)
        popup.setContentView(R.layout.popup_convert)

        //popupRef = popup

        val convertTitle = popup.findViewById<TextView>(R.id.survey_title_convert)

        holder.setIsRecyclable(false)

        // element courant avec position
        val currentSurvey = surveyList[position]

        // lien vers la db
        //val repo = SurveyRepository()

        // Maj du composant courant (titre et description)
        holder.surveyName.text = currentSurvey.title
        holder.surveyDescription.text = currentSurvey.description

        // Couleur differente 1/2
        if (position%2==1)
            holder.collectionItem.setCardBackgroundColor(Color.GRAY)

        // Click Button "Détails"
        holder.surveyDetails.setOnClickListener {

        }
        // Click "Convert"
        holder.buttonConvert.setOnClickListener {

            //convertTitle.text = currentSurvey.title
            convertTitle.setText(currentSurvey.title.drop(3))

            popup.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM

            popup.setCancelable(true)
            popup.setCanceledOnTouchOutside(true)

            popup.show()

        }

        // Click odt/pdf/png

        popup.findViewById<CardView>(R.id.convert_item_odt).setOnClickListener {

            when(selectedConvert) {

                -1 -> {
                    selectedConvert = 0
                    popup.findViewById<CardView>(R.id.convert_item_odt).setCardBackgroundColor(Color.rgb(230,230,230))
                }

                1 -> {
                    popup.findViewById<CardView>(R.id.convert_item_pdf).setCardBackgroundColor(Color.WHITE)
                    selectedConvert = 0
                    popup.findViewById<CardView>(R.id.convert_item_odt).setCardBackgroundColor(Color.rgb(230,230,230))

                }
                2 -> {
                    popup.findViewById<CardView>(R.id.convert_item_png).setCardBackgroundColor(Color.WHITE)
                    selectedConvert = 0
                    popup.findViewById<CardView>(R.id.convert_item_odt).setCardBackgroundColor(Color.rgb(230,230,230))
                }

            }
        }

        popup.findViewById<CardView>(R.id.convert_item_pdf).setOnClickListener {

            when(selectedConvert) {

                -1 -> {
                    selectedConvert = 1
                    popup.findViewById<CardView>(R.id.convert_item_pdf).setCardBackgroundColor(Color.rgb(230,230,230))
                }

                0 -> {
                    popup.findViewById<CardView>(R.id.convert_item_odt).setCardBackgroundColor(Color.WHITE)
                    selectedConvert = 1
                    popup.findViewById<CardView>(R.id.convert_item_pdf).setCardBackgroundColor(Color.rgb(230,230,230))
                }
                2 -> {
                    popup.findViewById<CardView>(R.id.convert_item_png).setCardBackgroundColor(Color.WHITE)
                    selectedConvert = 1
                    popup.findViewById<CardView>(R.id.convert_item_pdf).setCardBackgroundColor(Color.rgb(230,230,230))
                }

            }
        }

        popup.findViewById<CardView>(R.id.convert_item_png).setOnClickListener {

            when(selectedConvert) {

                -1 -> {
                    selectedConvert = 2
                    popup.findViewById<CardView>(R.id.convert_item_png).setCardBackgroundColor(Color.rgb(230,230,230))
                }

                1 -> {
                    popup.findViewById<CardView>(R.id.convert_item_pdf).setCardBackgroundColor(Color.WHITE)
                    selectedConvert = 2
                    popup.findViewById<CardView>(R.id.convert_item_png).setCardBackgroundColor(Color.rgb(230,230,230))
                }
                0 -> {
                    popup.findViewById<CardView>(R.id.convert_item_odt).setCardBackgroundColor(Color.WHITE)
                    selectedConvert = 2
                    popup.findViewById<CardView>(R.id.convert_item_png).setCardBackgroundColor(Color.rgb(230,230,230))
                }

            }
        }


            popup.findViewById<Button>(R.id.convert_button).setOnClickListener{
            when(selectedConvert) {

                // TODO : odt listener
                //odt
                0 -> {
                    val intent = Intent(context, ConvertActivity::class.java)

                    intent.putExtra("SURVEY", currentSurvey)
                    intent.putExtra("FORMAT", "odt")
                    context.startActivity(intent)

                }
                //pdf
                1 -> {
                    //popup.findViewById<Button>(R.id.convert_button).setText("test")
                    val intent = Intent(context, ConvertActivity::class.java)

                    intent.putExtra("SURVEY", currentSurvey)
                    intent.putExtra("FORMAT", "pdf")
                    context.startActivity(intent)
                }
                //png
                2 -> {
                    val intent = Intent(context, ConvertActivity::class.java)

                    intent.putExtra("SURVEY", currentSurvey)
                    intent.putExtra("FORMAT", "png")
                    context.startActivity(intent)
                }
            }
        }

        // TODO : creation DetailActivity pour la visualisation des questionnaires


    }


    /*
    *   Nombre d'item à afficher
    *
    */
    override fun getItemCount(): Int = surveyList.size

}