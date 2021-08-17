package com.androiddevs.mvvmnewsapp.ui

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
        val user= User()
        userSession.storeUser(user)
        val s=userSession.getUser().email
         etEmailAddress.editText?.setText(s)
    }



}