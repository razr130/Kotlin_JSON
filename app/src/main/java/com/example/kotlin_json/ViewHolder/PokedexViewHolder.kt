package com.example.kotlin_json.ViewHolder

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.view.menu.MenuView
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlin_json.*
import com.example.kotlin_json.Model.Answer
import com.example.kotlin_json.Model.Pokedex
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_upload_file_practice.*
import kotlinx.android.synthetic.main.answer_dialog.*
import kotlinx.android.synthetic.main.answer_dialog.view.*
import kotlinx.android.synthetic.main.answer_dialog.view.TxtTestAnswer
import kotlinx.android.synthetic.main.pokedex_layout.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.security.AccessController.getContext

class PokedexViewHolder(private val pokedexlist: ArrayList<Pokedex>) :
    RecyclerView.Adapter<PokedexViewHolder.PokedexHolder>() {


    class PokedexHolder(itemView: View, var pokedex: Pokedex? = null, var i: Int? = null) : RecyclerView.ViewHolder(itemView) {
        var txtpokemonname = itemView.findViewById(R.id.TxtPokemonName) as TextView
        var txtpokedexno = itemView.findViewById(R.id.TxtPokedexNo) as TextView
        var type1 = itemView.findViewById(R.id.IconType1) as Button
        var type2 = itemView.findViewById(R.id.IconType2) as Button
        var pokemonimg = itemView.findViewById(R.id.PictPokemon) as ImageView
        var card = itemView.findViewById(R.id.pokedex_card) as CardView

        init {
            itemView.pokedex_menu.setOnClickListener {
                val popupmenu = PopupMenu(itemView.context, it)
                popupmenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_edit -> {
                            val intent = Intent(itemView.context, EditPokemonActivity::class.java)
                            intent.putExtra("name", pokedex?.name)
                            intent.putExtra("id", pokedex?.id)
                            intent.putExtra("type", pokedex?.type)
                            itemView.context.startActivity(intent)
                            true
                        }
                        R.id.menu_delete -> {
                            val queue = Volley.newRequestQueue(itemView.context)
                            val url = Constant.BASE_URL + pokedex?.id
                            val arrayRequest = StringRequest(
                                Request.Method.DELETE, url, Response.Listener<String>
                                {
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
            itemView.setOnClickListener {
//                val intent = Intent(itemView.context, DetailPokemonActivity::class.java)
//                intent.putExtra("id", pokedex)
//                itemView.context.startActivity(intent)
                val intent = Intent(itemView.context, AddPokemonActivity::class.java)
                intent.putExtra("object", pokedex)
                intent.putExtra("index", i)
                itemView.context.startActivity(intent)
            }
        }

    }

    fun update(poke: Pokedex, i: Int){
        pokedexlist.set(i, poke)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PokedexHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.pokedex_layout, p0, false)
        return PokedexHolder(v)
    }

    override fun getItemCount(): Int {
        return pokedexlist.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: PokedexHolder, i: Int) {
        val imgurl =  Constant.BASE_URL + "Content/Images/"
        val pokedex: Pokedex = pokedexlist[i]
        val type: Array<String> = pokedex.type.split(" ").toTypedArray()
        holder.txtpokedexno.text = pokedex.id
        holder.txtpokemonname.text = pokedex.name
        if(pokedex.isanswer){
            holder.txtpokedexno.text = "kejawabbbb"
        }
        if (type[1].isEmpty() || type[1] == "") {
            holder.type2.visibility = View.GONE
        } else {
            holder.type2.text = type[1]
            if (holder.type2.text == "Normal") {
                holder.type2.setBackgroundResource(R.drawable.icon_normal)
            } else if (holder.type2.text == "Bug") {
                holder.type2.setBackgroundResource(R.drawable.icon_bug)
            } else if (holder.type2.text == "Dark") {
                holder.type2.setBackgroundResource(R.drawable.icon_dark)
            } else if (holder.type2.text == "Dragon") {
                holder.type2.setBackgroundResource(R.drawable.icon_dragon)
            } else if (holder.type2.text == "Electric") {
                holder.type2.setBackgroundResource(R.drawable.icon_electric)
            } else if (holder.type2.text == "Fairy") {
                holder.type2.setBackgroundResource(R.drawable.icon_fairy)
            } else if (holder.type2.text == "Fighting") {
                holder.type2.setBackgroundResource(R.drawable.icon_fighting)
            } else if (holder.type2.text == "Fire") {
                holder.type2.setBackgroundResource(R.drawable.icon_fire)
            } else if (holder.type2.text == "Flying") {
                holder.type2.setBackgroundResource(R.drawable.icon_flying)
            } else if (holder.type2.text == "Ghost") {
                holder.type2.setBackgroundResource(R.drawable.icon_ghost)
            } else if (holder.type2.text == "Grass") {
                holder.type2.setBackgroundResource(R.drawable.icon_grass)
            } else if (holder.type2.text == "Ground") {
                holder.type2.setBackgroundResource(R.drawable.icon_ground)
            } else if (holder.type2.text == "Ice") {
                holder.type2.setBackgroundResource(R.drawable.icon_ice)
            } else if (holder.type2.text == "Poison") {
                holder.type2.setBackgroundResource(R.drawable.icon_poison)
            } else if (holder.type2.text == "Psychic") {
                holder.type2.setBackgroundResource(R.drawable.icon_psychic)
            } else if (holder.type2.text == "Rock") {
                holder.type2.setBackgroundResource(R.drawable.icon_rock)
            } else if (holder.type2.text == "Steel") {
                holder.type2.setBackgroundResource(R.drawable.icon_steel)
            } else if (holder.type2.text == "Water") {
                holder.type2.setBackgroundResource(R.drawable.icon_water)
            }

        }

        holder.type1.text = type[0]
        if (holder.type1.text == "Normal") {
            holder.type1.setBackgroundResource(R.drawable.icon_normal)
            holder.card.setBackgroundResource(R.color.normal_lighter)
        } else if (holder.type1.text == "Bug") {
            holder.type1.setBackgroundResource(R.drawable.icon_bug)
            holder.card.setBackgroundResource(R.color.bug_lighter)
        } else if (holder.type1.text == "Dark") {
            holder.type1.setBackgroundResource(R.drawable.icon_dark)
            holder.card.setBackgroundResource(R.color.dark_lighter)
        } else if (holder.type1.text == "Dragon") {
            holder.type1.setBackgroundResource(R.drawable.icon_dragon)
            holder.card.setBackgroundResource(R.color.dragon_lighter)
        } else if (holder.type1.text == "Electric") {
            holder.type1.setBackgroundResource(R.drawable.icon_electric)
            holder.card.setBackgroundResource(R.color.electric_lighter)
        } else if (holder.type1.text == "Fairy") {
            holder.type1.setBackgroundResource(R.drawable.icon_fairy)
            holder.card.setBackgroundResource(R.color.fairy_lighter)
        } else if (holder.type1.text == "Fighting") {
            holder.type1.setBackgroundResource(R.drawable.icon_fighting)
            holder.card.setBackgroundResource(R.color.fighting_lighter)
        } else if (holder.type1.text == "Fire") {
            holder.type1.setBackgroundResource(R.drawable.icon_fire)
            holder.card.setBackgroundResource(R.color.fire_lighter)
        } else if (holder.type1.text == "Flying") {
            holder.type1.setBackgroundResource(R.drawable.icon_flying)
            holder.card.setBackgroundResource(R.color.flying_lighter)
        } else if (holder.type1.text == "Ghost") {
            holder.type1.setBackgroundResource(R.drawable.icon_ghost)
            holder.card.setBackgroundResource(R.color.ghost_lighter)
        } else if (holder.type1.text == "Grass") {
            holder.type1.setBackgroundResource(R.drawable.icon_grass)
            holder.card.setBackgroundResource(R.color.grass_lighter)
        } else if (holder.type1.text == "Ground") {
            holder.type1.setBackgroundResource(R.drawable.icon_ground)
            holder.card.setBackgroundResource(R.color.ground_lighter)
        } else if (holder.type1.text == "Ice") {
            holder.type1.setBackgroundResource(R.drawable.icon_ice)
            holder.card.setBackgroundResource(R.color.ice_lighter)
        } else if (holder.type1.text == "Poison") {
            holder.type1.setBackgroundResource(R.drawable.icon_poison)
            holder.card.setBackgroundResource(R.color.poison_lighter)
        } else if (holder.type1.text == "Psychic") {
            holder.type1.setBackgroundResource(R.drawable.icon_psychic)
            holder.card.setBackgroundResource(R.color.psychic_lighter)
        } else if (holder.type1.text == "Rock") {
            holder.type1.setBackgroundResource(R.drawable.icon_rock)
            holder.card.setBackgroundResource(R.color.rock_lighter)
        } else if (holder.type1.text == "Steel") {
            holder.type1.setBackgroundResource(R.drawable.icon_steel)
            holder.card.setBackgroundResource(R.color.steel_lighter)
        } else if (holder.type1.text == "Water") {
            holder.type1.setBackgroundResource(R.drawable.icon_water)
            holder.card.setBackgroundResource(R.color.water_lighter)
        }

        if (pokedex.image == "empty") {
            val context: Context = holder.pokemonimg.getContext()
            var id = context.resources.getIdentifier("pokeball", "drawable", context.packageName)
            holder.pokemonimg.setImageResource(id)
        } else {
            val context: Context = holder.pokemonimg.getContext()
            Picasso.with(context).load(imgurl + pokedex.image).fit().centerInside().into(holder.pokemonimg)
        }

        holder.pokedex = pokedex
        holder.i = i

    }

}