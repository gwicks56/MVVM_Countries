package com.gwicks.countries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gwicks.countries.R
import com.gwicks.countries.model.Country
import com.gwicks.countries.util.getProgressDrawable
import com.gwicks.countries.util.loadImage
import kotlinx.android.synthetic.main.item_country.view.*
import org.w3c.dom.Text

class CountryListAdapter(var countries: ArrayList<Country>):
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
    )

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val countryName:TextView = view.findViewById<TextView>(R.id.name)
        private val imageView = view.image_view
        private val countryCapital = view.capital
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(country: Country){
            countryName.text = country.countryName
            countryCapital.text = country.capital
            imageView.loadImage(country.flag, progressDrawable)

        }

    }


    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount() = countries.size
}