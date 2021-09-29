package com.androiddevs.mvvmnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import kotlinx.android.synthetic.main.activity_drawer.*

class DrawerActivity : AppCompatActivity() {

    lateinit var toggle :ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        toggle= ActionBarDrawerToggle(this,drawerLayout,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()




       navView.setNavigationItemSelectedListener{
           when(it.itemId)
           {
               R.id.miCategory-> Toast.makeText(applicationContext,"Clicked ",Toast.LENGTH_SHORT).show()
               R.id.miAccount-> Toast.makeText(applicationContext,"Clicked ",Toast.LENGTH_SHORT).show()
               R.id.miCountry-> Toast.makeText(applicationContext,"Clicked ",Toast.LENGTH_SHORT).show()


           }
           true

       }
        hamMenu1.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}