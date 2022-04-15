package bigboss.team.learnpenguin

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import bigboss.team.learnpenguin.Model.FavNewsObject
import bigboss.team.learnpenguin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var favNewsObjectList : ArrayList<FavNewsObject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        favNewsObjectList = arrayListOf()
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_main, R.id.navigation_news, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
            navController.popBackStack(item.itemId, inclusive = false)
        }
    }

    //Back button function
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            android.R.id.home -> {
                this.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun mainMenu(view: View?) {
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.selectedItemId = R.id.navigation_main
    }

    fun addFavNews(favNewsObject : FavNewsObject){
        if(!favNewsObjectList.contains(favNewsObject))
        {
            favNewsObjectList.add(favNewsObject)
            Toast.makeText(this,"Favourite News Added", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this,"News Already Added", Toast.LENGTH_SHORT).show()
        }

    }

    fun removeFavNews(i: Int){
        favNewsObjectList.removeAt(i)
        Toast.makeText(this,"Favourite News Removed", Toast.LENGTH_SHORT).show()
    }

    fun getFavNewsObjectList(): ArrayList<FavNewsObject> {
        return favNewsObjectList
    }
}