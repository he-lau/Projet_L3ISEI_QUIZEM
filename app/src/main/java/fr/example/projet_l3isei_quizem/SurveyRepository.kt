package fr.example.projet_l3isei_quizem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.example.projet_l3isei_quizem.model.Survey

/*
*   Lien avec FireBase
*/
class SurveyRepository {

    /*
    *   Singleton pour ne pas creer d'instance superflus lorsque l'on souhaite accéder aux valeurs
    */
    object Singleton {
        // connection a la base "surveys"
        val dbRef = FirebaseDatabase.getInstance().getReference("surveys")
        // liste des questionnaires
        val surveyList = arrayListOf<Survey>()
    }

    /*
    *   Maj Singleton.surveyList via dbRef
    *   callback : pouvoir laisser le temps au programme
    *   de charger les donnees depuis la db PUIS SEULEMENT d'afficher le fragment
    *   Unit : void Java
    */
    fun updateData(callback:() -> Unit) {

        // Listener sur la reference
        Singleton.dbRef.addValueEventListener(object: ValueEventListener{

            /*
            *   A DataSnapshot instance contains data from a Firebase Database location.
            *   Any time you read Database data, you receive the data as a DataSnapshot.
            *
            *   onDataChange() : déclenchée une fois lorsque l'écouteur est attaché et à chaque fois
            *   que les données, y compris les enfants, changent.
            */
            override fun onDataChange(snapshot: DataSnapshot) {
                // vider la liste
                Singleton.surveyList.clear()

                // parcours db chaque element (cf. JSON)
                for (ds in snapshot.children) {
                    // appel du constructeur Survey avec arguments de la db
                    val survey = ds.getValue(Survey::class.java)
                    // null Exception
                    if (survey != null) {
                        // ajout à la liste
                        Singleton.surveyList.add(survey)
                    }
                }
                // appel callback
                callback()
            }

            /*
            *
            */
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    /*
    *   Injection vers bd
    */

    fun writeNewSurvey(survey:Survey) {
        Singleton.dbRef.child(survey.id).setValue(survey)
    }



}