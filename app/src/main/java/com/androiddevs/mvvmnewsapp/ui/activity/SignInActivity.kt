package com.androiddevs.mvvmnewsapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.models.User
import com.androiddevs.mvvmnewsapp.preferences.SharedPreferences
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val userSession=SharedPreferences(applicationContext);
        //val user= User()
       // userSession.storeUser(user)
        btnSignIn.setOnClickListener {
            Intent(applicationContext, NewsActivity::class.java).also {
                startActivity(it)}
        }
    tvCreateAccount.setOnClickListener {
        Intent(applicationContext, SignUpActivity::class.java).also {
            startActivity(it)}
    }
    }




}