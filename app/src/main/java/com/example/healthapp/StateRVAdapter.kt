package com.example.healthapp

import android.view.LayoutInflater
import android.widget.TextView
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*


// the state adapter class is used to set the data to each item in the
// recycler view layout
class StateRVAdapter (private val stateList:List<StateModel>) :
    Adapter<StateRVAdapter.StateViewHolder>() {



    // create a holder class
    class StateViewHolder(itemView: View) : ViewHolder(itemView){
        val stateNameTV: TextView = itemView.findViewById(R.id.idRSA)
        val casesTV: TextView = itemView.findViewById(R.id.idTVSACases)
        val deathsTV: TextView = itemView.findViewById(R.id.idTVStateDeaths)
        val recoveredTV: TextView = itemView.findViewById(R.id.idTVSARecovered)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : StateViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.state_rv_item,parent,false)
        return StateViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int){
        val stateData = stateList[position]
        holder.casesTV.text = stateData.cases.toString()
        holder.stateNameTV.text = stateData.state
        holder.deathsTV.text = stateData.deaths.toString()
        holder.recoveredTV.text = stateData.recovered.toString()
    }

    override fun getItemCount(): Int {
        return  stateList.size

    }
}