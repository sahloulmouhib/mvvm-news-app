package com.androiddevs.mvvmnewsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.fragment.BreakingNewsFragment
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_temp.*


class CategoryActivity : AppCompatActivity() {

    lateinit var viewModel : NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        cvGeneral1.setOnClickListener {
            Intent(this, NewsActivity::class.java).also {
                startActivity(it)
        }


    }
}}