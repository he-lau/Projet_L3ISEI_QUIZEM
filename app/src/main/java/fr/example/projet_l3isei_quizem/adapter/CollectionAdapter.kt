package fr.example.projet_l3isei_quizem.adapter

import android.app.Dialog
import android.graphics.Color
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.example.projet_l3isei_quizem.MainActivity
import fr.example.projet_l3isei_quizem.R
import fr.example.projet_l3isei_quizem.SurveyRepository
import fr.example.projet_l3isei_quizem.model.Survey

/*
*   adapter : permet de passé du model (DB, Array) à des composants UI
*   ici les données seront fournies à RecycleView
 */
class CollectionAdapter(

    private var surveyList: List<Survey>,
    val context: MainActivity

) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>(){

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
        // element courant avec position
        val currentSurvey = surveyList[position]

        // lien vers la db
        val repo = SurveyRepository()

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

            // Popup
            var popup:Dialog = Dialog(context, R.style.WideDialog)
            popup.setContentView(R.layout.popup_convert)
            popup.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;

            popup.setCancelable(true);
            popup.setCanceledOnTouchOutside(true);

            popup.show()

        }
        // Click odt/pdf/png
    }

    /*
    *   Nombre d'item à afficher
    *
    */
    override fun getItemCount(): Int = surveyList.size
}