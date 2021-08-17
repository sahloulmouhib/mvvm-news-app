package com.androiddevs.mvvmnewsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_drawer.drawerLayout
import kotlinx.android.synthetic.main.activity_drawer.navView
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_news.bottomNavigationView
import kotlinx.android.synthetic.main.activity_news.newsNavHostFragment
import kotlinx.android.synthetic.main.item_article_preview.*
import kotlinx.android.synthetic.main.temp_drawer.*

class NewsActivity: AppCompatActivity() {

    lateinit var toggle : ActionBarDrawerToggle

    lateinit var viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.temp_drawer)


        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory=NewsViewModelProviderFactory(application,newsRepository)
        viewModel= ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())




        toggle= ActionBarDrawerToggle(this,drawerLayout,0,0)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()




        navView.setNavigationItemSelectedListener{
            when(it.itemId)
            {
                R.id.miCategory-> {Toast.makeText(applicationContext,"Clicked ", Toast.LENGTH_SHORT).show()
                    Intent(this, CategoryActivity::class.java).also {
                        startActivity(it)
                    }
                }
                R.id.miAccount-> Toast.makeText(applicationContext,"Clicked ", Toast.LENGTH_SHORT).show()
                R.id.miLanguage-> Toast.makeText(applicationContext,"Clicked ", Toast.LENGTH_SHORT).show()
            }
            true

        }
        hamMenu.setOnClickListener {
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