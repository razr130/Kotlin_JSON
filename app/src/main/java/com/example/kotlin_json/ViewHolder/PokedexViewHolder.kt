package com.example.kotlin_json.ViewHolder

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlin_json.EditPokemonActivity
import com.example.kotlin_json.MainActivity
import com.example.kotlin_json.Model.Pokedex
import com.example.kotlin_json.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokedex_layout.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.security.AccessController.getContext

class PokedexViewHolder (private val pokedexlist:ArrayList<Pokedex>): RecyclerView.Adapter<PokedexViewHolder.PokedexHolder>() {



    class PokedexHolder(itemView: View, var pokedex: Pokedex? = null) : RecyclerView.ViewHolder(itemView) {
        var txtpokemonname = itemView.findViewById(R.id.TxtPokemonName) as TextView
        var txtpokedexno = itemView.findViewById(R.id.TxtPokedexNo) as TextView
        var txttype = itemView.findViewById(R.id.TxtType) as TextView
        var pokemonimg = itemView.findViewById(R.id.PictPokemon) as ImageView

        init {
//            itemView.setOnClickListener {
//
//
//            }
            itemView.pokedex_menu.setOnClickListener {
                val popupmenu = PopupMenu(itemView.context, it)
                popupmenu.setOnMenuItemClickListener {
                    item ->
                    when (item.itemId){
                        R.id.menu_edit -> {
                            val intent = Intent(itemView.context, EditPokemonActivity::class.java)
                            intent.putExtra("name",pokedex?.name)
                            intent.putExtra("id", pokedex?.id)
                            intent.putExtra("type",pokedex?.type)
                            itemView.context.startActivity(intent)
                            true
                        }
                        R.id.menu_delete -> {
                            val queue = Volley.newRequestQueue(itemView.context)
                            val url = "http://192.168.2.49:3000/pokemon/" + pokedex?.id
                            val arrayRequest = StringRequest(
                                Request.Method.DELETE, url, Response.Listener<String>
                                {
                                        _ ->

                                    Toast.makeText(itemView.context, "Pokemon data deleted", Toast.LENGTH_LONG).show()
                                },
                                Response.ErrorListener
                                { error ->
                                    Toast.makeText(itemView.context, error.toString(), Toast.LENGTH_LONG).show()
                                })
                            queue.add(arrayRequest)

                            true
                        }
                        else -> false
                    }
                }
                popupmenu.inflate(R.menu.menu_pokedex)
                popupmenu.show()
            }
        }

    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PokedexHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.pokedex_layout, p0, false)
        return PokedexHolder(v)
    }

    override fun getItemCount(): Int {
        return pokedexlist.size
    }

    override fun onBindViewHolder(holder: PokedexHolder, i: Int) {
        val pokedex: Pokedex = pokedexlist[i]

        holder.txtpokedexno.text = pokedex.id
        holder.txtpokemonname.text = pokedex.name
        holder.txttype.text = pokedex.type

        if(pokedex.image=="empty")
        {
            val context: Context = holder.pokemonimg.getContext()
            var id = context.resources.getIdentifier("pokeball", "drawable", context.packageName)
            holder.pokemonimg.setImageResource(id)
        }
        else
        {
            val context: Context = holder.pokemonimg.getContext()
            Picasso.with(context).load(pokedex.image).fit().centerInside().into(holder.pokemonimg)
        }

        holder.pokedex = pokedex

    }

}