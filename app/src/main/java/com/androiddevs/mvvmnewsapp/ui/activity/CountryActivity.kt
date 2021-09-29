package com.androiddevs.mvvmnewsapp.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapters.CountriesAdapter
import com.androiddevs.mvvmnewsapp.models.Country
import com.androiddevs.mvvmnewsapp.preferences.SharedPreferences
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_country.*
import java.util.*
import kotlin.collections.ArrayList

class CountryActivity : AppCompatActivity() {
    private lateinit var countriesRecyclerView :RecyclerView

    private lateinit var countriesList:ArrayList<Country>
    private lateinit var tempCountriesList:ArrayList<Country>

    lateinit var userSession: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)
        userSession=SharedPreferences(this)



        countriesList = ArrayList<Country>()
        tempCountriesList = ArrayList<Country>()


        rvCountries.layoutManager = LinearLayoutManager(this)
        rvCountries.setHasFixedSize(true)


        val adapter = CountriesAdapter(tempCountriesList)
        rvCountries.adapter = adapter


        addCountries()
        tempCountriesList.addAll(countriesList)


        ivBackCountry.setOnClickListener {
            finish()
        }


        svCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
          override fun onQueryTextSubmit(query: String?): Boolean {
             return false
          }

          override fun onQueryTextChange(newText: String?): Boolean {

              val searchText=newText!!.toLowerCase(Locale.getDefault())
              if(searchText.isNotEmpty())
              {
                  tempCountriesList.clear()
                    tempCountriesList.addAll(countriesList.filter { it.name.toLowerCase(Locale.getDefault()).contains(searchText) })
                 val size= tempCountriesList.size
                  rvCountries.adapter!!.notifyDataSetChanged()
              }
              else
              {
                  tempCountriesList.clear()
                  tempCountriesList.addAll(countriesList)
                  rvCountries.adapter!!.notifyDataSetChanged()

              }

              return false
          }

      })







        adapter.setOnItemClickListener(object : CountriesAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val c=tempCountriesList[position]
               // Toast.makeText(this@CountryActivity, "I am  ${tempCountriesList[position].name} and my code is ${tempCountriesList[position].code}", Toast.LENGTH_SHORT).show()
                userSession.storeCountry(tempCountriesList[position])
                Intent(applicationContext,NewsActivity::class.java).also{
                    startActivity(it)

                }

            }

        })


    }
    fun addCountries()
    {


        val countriesName= arrayListOf("Argentina\n" ,
            "Australia\n" ,
            "Austria\n" ,
            "Belgium\n" ,
            "Brazil\n" ,
            "Bulgaria\n" ,
            "Canada\n" ,
            "China\n" ,
            "Colombia\n" ,
            "Cuba\n" ,
            "Czech Republic\n" ,
            "Egypt\n" ,
            "France\n" ,
            "Germany\n" ,
            "Greece\n" ,
            "Hong Kong\n" ,
            "Hungary\n" ,
            "India\n" ,
            "Indonesia\n" ,
            "Ireland\n" ,

            "Italy\n" ,
            "Japan\n" ,
            "Latvia\n" ,
            "Lithuania\n" ,
            "Malaysia\n" ,
            "Mexico\n" ,
            "Morocco\n" ,
            "Netherlands\n" ,
            "New Zealand\n" ,
            "Nigeria\n" ,
            "Norway\n" ,
            "Philippines\n" ,
            "Poland\n" ,
            "Portugal\n" ,
            "Romania\n" ,
            "Russia\n" ,
            "Saudi Arabia\n" ,
            "Serbia\n" ,
            "Singapore\n" ,
            "Slovakia\n" ,
            "Slovenia\n" ,
            "South Africa\n" ,
            "South Korea\n" ,
            "Sweden\n" ,
            "Switzerland\n" ,
            "Taiwan\n" ,
            "Thailand\n" ,
            "Turkey\n" ,
            "UAE\n" ,
            "Ukraine\n" ,
            "United Kingdom\n" ,
            "United States\n" ,
            "Venuzuela")
        val tempCountriesName=ArrayList<String>()

        for( item in countriesName)
        {

            tempCountriesName.add(item.trim())

        }

        val countriesFlags= arrayOf(
            R.drawable.argentina,
            R.drawable.australia,
            R.drawable.austria,
            R.drawable.belgium
            ,
            R.drawable.brazil,
            R.drawable.bulgaria,
            R.drawable.canada,
            R.drawable.china,
            R.drawable.colombia,
            R.drawable.cuba
            ,
            R.drawable.czech_republic,
            R.drawable.egypt,
            R.drawable.france,
            R.drawable.germany,
            R.drawable.greece,
            R.drawable.hong_kong
            ,
            R.drawable.hungary,
            R.drawable.india,
            R.drawable.indonesia,
            R.drawable.ireland,

            R.drawable.italy
            ,
            R.drawable.japan,
            R.drawable.latvia,
            R.drawable.lithuania,
            R.drawable.malaysia,
            R.drawable.mexico,
            R.drawable.morocco
            ,
            R.drawable.netherlands,
            R.drawable.new_zealand,
            R.drawable.nigeria,
            R.drawable.norway,
            R.drawable.philippines
            ,
            R.drawable.poland,
            R.drawable.portugal,
            R.drawable.romania,
            R.drawable.russia,
            R.drawable.saudi_arabia,
            R.drawable.serbia
            ,
            R.drawable.singapore,
            R.drawable.slovakia,
            R.drawable.slovenia,
            R.drawable.south_africa,
            R.drawable.south_korea
            ,
            R.drawable.sweden,
            R.drawable.switzerland,
            R.drawable.taiwan,
            R.drawable.thailand,
            R.drawable.turkey,
            R.drawable.united_arab_emirates
            ,
            R.drawable.ukraine,
            R.drawable.united_kingdom,
            R.drawable.united_states,
            R.drawable.venezuela
        )

        val countriesCodes=arrayOf("ar","au","at","be","br","bg","ca","cn","co","cu","cz","eg","fr","de","gr","hk","hu","in","id","ie","it","jb",
            "lv","lt","my","mx","ma","nl","nz","ng","no","ph","pl","pt","ro","ru","sa","rs","sg","sk","si","za","kr","se","ch","tw","th","tr","ae","ua","gb","us","ve")



        for (i in 0..52) {
            countriesList.add(Country(tempCountriesName[i],countriesFlags[i],countriesCodes[i]))
        }
    }

}
