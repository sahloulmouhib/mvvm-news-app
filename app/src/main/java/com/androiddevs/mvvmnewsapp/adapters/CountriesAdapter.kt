package com.androiddevs.mvvmnewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.models.Country
import kotlinx.android.synthetic.main.item_country_preview.view.*

class CountriesAdapter(private val countriesList: ArrayList<Country>) :
    RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {


    private lateinit var mListener: onItemClickListener

    interface onItemClickListener
    {
        fun onItemClick(position :Int)
    }

    fun setOnItemClickListener(listener :onItemClickListener)
    {
        mListener=listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
       val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_country_preview,parent,false)
        return CountryViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int)
    {
        val currentItem=countriesList[position]
        /*holder.countryImage.setImageResource(currentItem.image)
        holder.countryCode.text=currentItem.code
        holder.countryName.text=currentItem.name

         */
        holder.itemView.apply {
            ivCountryFlag.setImageResource(currentItem.image)
            tvCountryCode.text=currentItem.code
          tvCountryName.text=currentItem.name
        }
    }

    override fun getItemCount(): Int {
        return  countriesList.size
    }
    class CountryViewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView)
    {
       // val countryImage:ImageView =itemView.findViewById(R.id.ivCountryFlag)
        //val countryName:TextView=itemView.findViewById(R.id.tvCountryName)
        //val countryCode:TextView=itemView.findViewById(R.id.tvCountryCode)

        init {

        itemView.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
        }
    }
}
