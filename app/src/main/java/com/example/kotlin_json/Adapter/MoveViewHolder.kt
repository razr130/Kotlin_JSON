package com.example.kotlin_json.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.kotlin_json.Model.Move
import com.example.kotlin_json.R
import kotlinx.android.synthetic.main.move_layout.view.*

class MoveViewHolder(private val movelist: ArrayList<Move>) : RecyclerView.Adapter<MoveViewHolder.MoveHolder>(){
    class MoveHolder(itemView: View, var move: Move? = null, var i: Int? = null) : RecyclerView.ViewHolder(itemView){

        var categoryimg = itemView.findViewById(R.id.ImgMoveCategory) as ImageView
        var movetype = itemView.findViewById(R.id.MoveType) as Button
        var txtmovename = itemView.findViewById(R.id.TxtMoveName) as TextView
        var txtmovedmg = itemView.findViewById(R.id.TxtMoveDmg) as TextView
        var txtmoveeffect = itemView.findViewById(R.id.TxtMoveEffect) as TextView
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MoveHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.move_layout,p0,false)
        return MoveHolder(v)
    }

    override fun getItemCount(): Int {
        return movelist.size
    }

    override fun onBindViewHolder(holder: MoveHolder, i: Int) {

        val move: Move = movelist[i]
        val type = move.move_type
        val category = move.move_category
        holder.txtmovename.text = move.move_name
        holder.txtmovedmg.text = move.move_damage.toString()
        if(move.move_effect == "none"){
            holder.txtmoveeffect.text = ""
        }
        else{
            holder.txtmoveeffect.text = move.move_effect
        }
        holder.movetype.text = type
        if (holder.movetype.text == "Normal") {
            holder.movetype.setBackgroundResource(R.drawable.icon_normal)
        } else if (holder.movetype.text == "Bug") {
            holder.movetype.setBackgroundResource(R.drawable.icon_bug)
        } else if (holder.movetype.text == "Dark") {
            holder.movetype.setBackgroundResource(R.drawable.icon_dark)
        } else if (holder.movetype.text == "Dragon") {
            holder.movetype.setBackgroundResource(R.drawable.icon_dragon)
        } else if (holder.movetype.text == "Electric") {
            holder.movetype.setBackgroundResource(R.drawable.icon_electric)
        } else if (holder.movetype.text == "Fairy") {
            holder.movetype.setBackgroundResource(R.drawable.icon_fairy)
        } else if (holder.movetype.text == "Fighting") {
            holder.movetype.setBackgroundResource(R.drawable.icon_fighting)
        } else if (holder.movetype.text == "Fire") {
            holder.movetype.setBackgroundResource(R.drawable.icon_fire)
        } else if (holder.movetype.text == "Flying") {
            holder.movetype.setBackgroundResource(R.drawable.icon_flying)
        } else if (holder.movetype.text == "Ghost") {
            holder.movetype.setBackgroundResource(R.drawable.icon_ghost)
        } else if (holder.movetype.text == "Grass") {
            holder.movetype.setBackgroundResource(R.drawable.icon_grass)
        } else if (holder.movetype.text == "Ground") {
            holder.movetype.setBackgroundResource(R.drawable.icon_ground)
        } else if (holder.movetype.text == "Ice") {
            holder.movetype.setBackgroundResource(R.drawable.icon_ice)
        } else if (holder.movetype.text == "Poison") {
            holder.movetype.setBackgroundResource(R.drawable.icon_poison)
        } else if (holder.movetype.text == "Psychic") {
            holder.movetype.setBackgroundResource(R.drawable.icon_psychic)
        } else if (holder.movetype.text == "Rock") {
            holder.movetype.setBackgroundResource(R.drawable.icon_rock)
        } else if (holder.movetype.text == "Steel") {
            holder.movetype.setBackgroundResource(R.drawable.icon_steel)
        } else if (holder.movetype.text == "Water") {
            holder.movetype.setBackgroundResource(R.drawable.icon_water)
        }

        if(category == "Physical"){
            val context: Context = holder.categoryimg.getContext()
            var id = context.resources.getIdentifier("physical", "drawable", context.packageName)
            holder.categoryimg.setImageResource(id)
        }
        else if(category == "Special"){
            val context: Context = holder.categoryimg.getContext()
            var id = context.resources.getIdentifier("special", "drawable", context.packageName)
            holder.categoryimg.setImageResource(id)
        }
        else{
            val context: Context = holder.categoryimg.getContext()
            var id = context.resources.getIdentifier("status", "drawable", context.packageName)
            holder.categoryimg.setImageResource(id)
        }

    }


}