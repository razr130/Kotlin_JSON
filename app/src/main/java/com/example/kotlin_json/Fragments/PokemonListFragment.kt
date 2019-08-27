package com.example.kotlin_json.Fragments


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.kotlin_json.Adapter.PokedexViewHolder
import com.example.kotlin_json.AddPokemonActivity
import com.example.kotlin_json.Constant
import com.example.kotlin_json.Model.Pokedex
import com.example.kotlin_json.R
import com.example.kotlin_json.Singleton.VolleySingleton
import kotlinx.android.synthetic.main.fragment_pokemon_list.*
import org.json.JSONArray
import org.json.JSONObject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PokemonListFragment : Fragment() {

    var pokedexlist = ArrayList<Pokedex>()
    lateinit var recyclerView: RecyclerView
    var url: String = Constant.BASE_URL
    lateinit var madapter: PokedexViewHolder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        pokedexlist.clear()
        parseJSON()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = Recycler_pokemon
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        fab.setOnClickListener {
            var intent = Intent(activity, AddPokemonActivity::class.java)
            startActivity(intent)
        }

    }
    private fun parseJSON() {
        val loading = ProgressDialog(activity)
        loading.setMessage("Loading pokedex...")
        loading.show()
        loading.setCanceledOnTouchOutside(false)
        loading.setCancelable(false)

        val queue = VolleySingleton.getInstance(activity!!.applicationContext).requestQueue


        val arrayRequest = JsonArrayRequest(
            Request.Method.GET, url + "pokedex/get_pokedex", null, Response.Listener<JSONArray>
        { response ->
            for (k in 0 until response.length()) {
                val ob: JSONObject = response.getJSONObject(k)

                val dexno: String = ob.getString("pokedex_id")
                val name: String = ob.getString("pokemon_name")
                val pokeimg: String = ob.getString("image")
                var type = ""

                for (i in 0 until ob.getJSONArray("type").length()) {
                    type += ob.getJSONArray("type").getString(i) + " "
                }
                pokedexlist.add(Pokedex(dexno, name, pokeimg, type))

            }
        },
            Response.ErrorListener
            { error ->
                Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show()
            })

        arrayRequest.retryPolicy = DefaultRetryPolicy(
            60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        VolleySingleton.getInstance(activity!!.applicationContext).addToRequestQueue(arrayRequest)
        queue.addRequestFinishedListener(RequestQueue.RequestFinishedListener<String> {
            madapter = PokedexViewHolder(pokedexlist)
            recyclerView.adapter = madapter
            loading.dismiss()
        })
    }
}
