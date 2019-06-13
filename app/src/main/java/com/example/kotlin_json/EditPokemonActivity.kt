package com.example.kotlin_json

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.activity_add_pokemon.*
import kotlinx.android.synthetic.main.activity_edit_pokemon.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class EditPokemonActivity : AppCompatActivity() {

    val alltype = arrayOf("", "Normal", "Grass", "Fire", "Water", "Fighting",
        "Flying", "Poison", "Ground", "Rock", "Bug",
        "Ghost", "Electric", "Psychic", "Ice", "Dragon",
        "Dark", "Steel", "Fairy")
    var index1 = 0
    var index2 = 0
    var type = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pokemon)

        TxtPokedexNoEdit.isFocusable = false
        var rec_id = intent.getStringExtra("id").toString()
        TxtPokedexNoEdit.setText(rec_id)
        var rec_name = intent.getStringExtra("name").toString()
        TxtPokemonNameEdit.setText(rec_name)
        var rec_type = intent.getStringExtra("type")
        var type_splitted: Array<String> = rec_type.split(" ").toTypedArray()

        spinner3.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alltype)
        for(i in 0 until spinner3.count){
            if(spinner3.getItemAtPosition(i).equals(type_splitted.get(0)))
            {
                index1 = i
            }
        }
        spinner3.setSelection(index1)
        type.add(type_splitted.get(0))
        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                type.set(0, spinner3.getItemAtPosition(position).toString())
            }
        }

        spinner4.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alltype)
        for(i in 0 until spinner4.count){
            if(spinner4.getItemAtPosition(i).equals(type_splitted.get(1)))
            {
                index2 = i
            }
        }
        spinner4.setSelection(index2)
        if(type_splitted.get(1)!=null)
        {
            type.add(type_splitted.get(1))
        }
        else{

        }
        spinner4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(type_splitted.get(1)!=null)
                {
                    type.set(1, spinner4.getItemAtPosition(position).toString())
                }
                else
                {
                    type.add(spinner4.getItemAtPosition(position).toString())
                }

            }
        }

        BtnSaveEdit.setOnClickListener {
            editpokemon(TxtPokedexNoEdit.text.toString(), TxtPokemonNameEdit.text.toString(),type)
        }

    }

    private fun editpokemon(id: String, name: String, type: ArrayList<String>)
    {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.2.208:3000/pokemon/" + id

        var ob = JSONObject()
        var ar = JSONArray()

        for(i in 0 until type.size)
        {
            ar.put(type.get(i))
        }
        ob.put("id", id)
        ob.put("name", name)
        ob.put("type", ar)
        ob.put("image", "empty")

        val jsonObjectRequest =  JsonObjectRequest(Request.Method.PUT,url,ob, Response.Listener {
                response ->
            Toast.makeText(this, name + " data updated in Pokedex", Toast.LENGTH_SHORT).show()
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
