package fr.example.projet_l3isei_quizem

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import fr.example.projet_l3isei_quizem.fragments.CollectionFragment
import fr.example.projet_l3isei_quizem.fragments.HomeFragment

import com.google.android.material.bottomnavigation.BottomNavigationView

/*
*   R permet acceder au variables dans le dossier /res : R.id, R.layout...
*   OnCreate()
*   setContentView()
*   FragmentManager pour manipuler les fragments
*   addToBackStack : permet le retour en arriere de l'Activity
*/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navigationView = findViewById<BottomNavigationView>(R.id.navigation_view)

        loadFragment(HomeFragment())



        /*
        *   Action sur le menu et maj du fragment & bouton creation questionnaire
        */
        navigationView?.setOnItemSelectedListener { item ->
            when(item.itemId) {
                // Acceuil
                R.id.action_home_page -> {
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                // Collection
                R.id.action_collection_page -> {
                    //loadFragment(CollectionFragment(this))
                    loadFragment(CollectionFragment())
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }


    }


    /*
    *   Charger le fragment via support supportFragmentManager
    *   Maj base de donnee
    */
    private fun loadFragment(fragment:Fragment) {

        val repo = SurveyRepository()

        /*
        *  updateData + callback
        * */
        repo.updateData {

            val transaction = supportFragmentManager.beginTransaction()

            // FramLayout R.id.fragment_container
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()


        }

    }
}
