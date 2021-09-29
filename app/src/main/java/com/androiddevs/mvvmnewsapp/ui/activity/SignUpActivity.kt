package com.androiddevs.mvvmnewsapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androiddevs.mvvmnewsapp.R
import kotlinx.android.synthetic.main.activity_sin_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sin_up)

        tvBackSignUp.setOnClickListener {
            finish()
        }
    }
}