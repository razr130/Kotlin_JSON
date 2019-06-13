package com.example.kotlin_json

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.kotlin_json.Model.Pokedex
import com.example.kotlin_json.ViewHolder.PokedexViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_pokemon.*
import org.json.JSONArray
import org.json.JSONObject

class DetailPokemonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pokemon)

        var rec_id = intent.getStringExtra("id").toString()

        parseJSON(rec_id)

    }

    private fun parseJSON(id: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.2.208:3000/pokemon?id=" + id

        val arrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null, Response.Listener<JSONArray>
            { response ->
                for (k in 0 until response.length()) {
                    val ob: JSONObject = response.getJSONObject(k)

                    val dexno: String = ob.getString("id")
                    val name: String = ob.getString("name")
                    val pokeimg: String = ob.getString("image")
                    val species: String = ob.getString("species")
                    val height: String = ob.getString("height")
                    val weight: String = ob.getString("weight")
                    val abilities: String = ob.getString("abilities")

                    for (i in 0 until ob.getJSONArray("type").length()) {
//                        type += ob.getJSONArray("type").getString(i) + " "
                        if(i==0) {
                            IconType1Detail.text = ob.getJSONArray("type").getString(i)
                            if (ob.getJSONArray("type").getString(i) == "Normal") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_normal)
                            } else if (ob.getJSONArray("type").getString(i) == "Bug") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_bug)
                            } else if (ob.getJSONArray("type").getString(i) == "Dark") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_dark)
                            } else if (ob.getJSONArray("type").getString(i) == "Dragon") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_dragon)
                            } else if (ob.getJSONArray("type").getString(i) == "Electric") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_electric)
                            } else if (ob.getJSONArray("type").getString(i) == "Fairy") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_fairy)
                            } else if (ob.getJSONArray("type").getString(i) == "Fighting") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_fighting)
                            } else if (ob.getJSONArray("type").getString(i) == "Fire") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_fire)
                            } else if (ob.getJSONArray("type").getString(i) == "Flying") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_flying)
                            } else if (ob.getJSONArray("type").getString(i) == "Ghost") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_ghost)
                            } else if (ob.getJSONArray("type").getString(i) == "Grass") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_grass)
                            } else if (ob.getJSONArray("type").getString(i) == "Ground") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_ground)
                            } else if (ob.getJSONArray("type").getString(i) == "Ice") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_ice)
                            } else if (ob.getJSONArray("type").getString(i) == "Poison") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_poison)
                            } else if (ob.getJSONArray("type").getString(i) == "Psychic") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_psychic)
                            } else if (ob.getJSONArray("type").getString(i) == "Rock") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_rock)
                            } else if (ob.getJSONArray("type").getString(i) == "Steel") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_steel)
                            } else if (ob.getJSONArray("type").getString(i) == "Water") {
                                IconType1Detail.setBackgroundResource(R.drawable.icon_water)
                            }
                            IconType2Detail.visibility = View.GONE
                        }
                        if(i==1){
                            IconType2Detail.visibility = View.VISIBLE
                            IconType2Detail.text = ob.getJSONArray("type").getString(i)
                            if(ob.getJSONArray("type").getString(i) == "Normal"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_normal)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Bug"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_bug)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Dark"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_dark)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Dragon"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_dragon)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Electric"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_electric)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Fairy"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_fairy)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Fighting"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_fighting)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Fire"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_fire)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Flying"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_flying)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Ghost"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_ghost)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Grass"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_grass)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Ground"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_ground)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Ice"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_ice)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Poison"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_poison)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Psychic"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_psychic)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Rock"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_rock)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Steel"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_steel)
                            }
                            else if(ob.getJSONArray("type").getString(i) == "Water"){
                                IconType2Detail.setBackgroundResource(R.drawable.icon_water)
                            }
                        }
                    }

                    TxtNameDetail.text = name
                    TxtDexNoDetailData.text = dexno
                    TxtSpeciesDetailData.text = species
                    TxtHeightDetailData.text = height
                    TxtWeightDetailData.text = weight
                    TxtAbilitiesDetailData.text = abilities
                    Picasso.with(this).load(pokeimg).into(ImagePokemonDetail)
                }
            },
            Response.ErrorListener
            { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            })
        queue.add(arrayRequest)
    }
}
