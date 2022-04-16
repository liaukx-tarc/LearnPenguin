package bigboss.team.learnpenguin

import android.os.Bundle
import android.view.MenuItem
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var favNewsObjectList : ArrayList<FavNewsObject>
    private lateinit var userDatabase : DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userDatabase = FirebaseDatabase.getInstance().getReference("User")
        auth = FirebaseAuth.getInstance()
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
        userDatabase.child("User").child(auth.uid.toString()).get()
            .addOnSuccessListener{
                if(it.hasChild("favNewsList"))
                {
                    for(i in 0 until it.child("favNewsList").childrenCount)
                    {
                        val favNewsObject = FavNewsObject(
                            it.child("favNewsList").child(i.toString()).child("title").value.toString()
                            , it.child("favNewsList").child(i.toString()).child("pubDate").value.toString()
                            , it.child("favNewsList").child(i.toString()).child("thumbnail").value.toString()
                            , it.child("favNewsList").child(i.toString()).child("link").value.toString())
                        favNewsObjectList.add(favNewsObject)
                    }
                }
        }
            .addOnFailureListener {
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
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

    fun mainMenu() {
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        navView.selectedItemId = R.id.navigation_main
    }

    fun addFavNews(favNewsObject : FavNewsObject){
        if(!favNewsObjectList.contains(favNewsObject))
        {
            favNewsObjectList.add(favNewsObject)
            userDatabase.child("User").child(auth.uid.toString()).child("favNewsList").child(favNewsObjectList.indexOf(favNewsObject).toString()).setValue(favNewsObject)
            Toast.makeText(this,"Favourite News Added", Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this,"News Already Added", Toast.LENGTH_SHORT).show()
        }

    }

    fun removeFavNews(i: Int){
        favNewsObjectList.removeAt(i)
        userDatabase.child("User").child(auth.uid.toString()).child("favNewsList").removeValue()
        for((index, item) in favNewsObjectList.withIndex())
        {
            userDatabase.child("User").child(auth.uid.toString()).child("favNewsList").child(index.toString()).setValue(item)
        }
        Toast.makeText(this,"Favourite News Removed", Toast.LENGTH_SHORT).show()
    }

    fun getFavNewsObjectList(): ArrayList<FavNewsObject> {
        return favNewsObjectList
    }
}