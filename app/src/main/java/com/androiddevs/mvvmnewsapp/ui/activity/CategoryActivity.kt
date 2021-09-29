package com.androiddevs.mvvmnewsapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.preferences.SharedPreferences
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.activity_category.*


class CategoryActivity : AppCompatActivity() , View.OnClickListener {
    lateinit var userSession:SharedPreferences
    lateinit var viewModel: NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        ivBackCategory.setOnClickListener {
            finish()
        }

        cvGeneralMain.setOnClickListener(this)
        cvBusiness.setOnClickListener(this)
        cvEntertainment.setOnClickListener(this)
        cvSports.setOnClickListener(this)
        cvTechnology.setOnClickListener(this)
        cvScience.setOnClickListener(this)
        cvHealth.setOnClickListener(this)




    }
    override fun onClick(view: View?)
    {
        when ( view!!.id)
        {
            R.id.cvGeneralMain->{ chooseCategory("general") }
            R.id.cvBusiness->{ chooseCategory("business") }
            R.id.cvEntertainment->{ chooseCategory("entertainment") }
            R.id.cvSports->{ chooseCategory("sports") }
            R.id.cvTechnology->{ chooseCategory("technology") }
            R.id.cvScience->{ chooseCategory("science") }
            R.id.cvHealth->{ chooseCategory("health") }

        }

    }
    fun chooseCategory(category: String)
    {
        userSession= SharedPreferences(applicationContext);
        userSession.storeGategory(category)
        Toast.makeText(this,"${userSession.getCategory()}",Toast.LENGTH_LONG).show()
        Intent(applicationContext, NewsActivity::class.java).also {
            startActivity(it)}
    }

}