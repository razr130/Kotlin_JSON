package com.example.kotlin_json

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.activity_add_pokemon.*
import org.json.JSONArray
import org.json.JSONObject

class AddPokemonActivity : AppCompatActivity() {

     var type = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pokemon)

        spinner.setItems(
            "", "Normal", "Grass", "Fire", "Water", "Fighting",
            "Flying", "Poison", "Ground", "Rock", "Bug",
            "Ghost", "Electric", "Psychic", "Ice", "Dragon",
            "Dark", "Steel", "Fairy"
        )
        spinner.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener<String> { _, _, _, item ->
            //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            type.add(item)
            Toast.makeText(this, type.get(0), Toast.LENGTH_LONG).show()
        })
        spinner2.setItems(
            "", "Normal", "Grass", "Fire", "Water", "Fighting",
            "Flying", "Poison", "Ground", "Rock", "Bug",
            "Ghost", "Electric", "Psychic", "Ice", "Dragon",
            "Dark", "Steel", "Fairy"
        )
        spinner2.setOnItemSelectedListener(MaterialSpinner.OnItemSelectedListener<String> { _, _, _, item ->
            //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            type.add(item)
            Toast.makeText(this, type.get(1), Toast.LENGTH_LONG).show()
        })

        BtnAdd.setOnClickListener {
            addpokemon(TxtPokedexNoAdd.text.toString(), TxtPokemonNameAdd.text.toString(), type, TxtPokemonSpeciesAdd.text.toString(),
                (TxtPokemonHeightAdd.text.toString()).toDouble(), (TxtPokemonWeightAdd.text.toString()).toDouble(),TxtPokemonAbilitiesAdd.text.toString(),
                (TxtPokemonHPAdd.text.toString()).toInt(),(TxtPokemonAttackAdd.text.toString()).toInt(),(TxtPokemonDefenseAdd.text.toString()).toInt(),
                (TxtPokemonSpAttackAdd.text.toString()).toInt(),(TxtPokemonSpDefenseAdd.text.toString()).toInt(),(TxtPokemonSpeedAdd.text.toString()).toInt())

        }

    }

    private fun addpokemon(id: String?, name: String?, type: ArrayList<String>, species: String?, height: Double?, weight: Double?, abilities: String?,
                           hp: Int?, attack: Int?, defense: Int?, spattack: Int?, spdefense: Int?, speed: Int?) {

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.2.94:3000/pokemon"

        var ob = JSONObject()
        var ar = JSONArray()

        for(i in 0 until type.size)
        {
            ar.put(type.get(i))
        }
        ob.put("id", id)
        ob.put("name", name)
        ob.put("type", ar)
        ob.put("species", species)
        ob.put("height", height)
        ob.put("weight", weight)
        ob.put("abilities", abilities)
        var ob2 = JSONObject()
        ob2.put("hp", hp)
        ob2.put("attack", attack)
        ob2.put("defense", defense)
        ob2.put("spattack", spattack)
        ob2.put("spdefense", spdefense)
        ob2.put("speed", speed)
        ob.put("stats",ob2)
        ob.put("image", "empty")

          val jsonObjectRequest =  JsonObjectRequest(Request.Method.POST,url,ob,Response.Listener {
            response ->
            Toast.makeText(this, name + " data added to Pokedex", Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener {
                error ->
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
        })
        queue.add(jsonObjectRequest)
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finish()
    }
}
