package com.example.kotlin_json

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.Volley
import com.example.kotlin_json.Model.Answer
import com.example.kotlin_json.Model.Pokedex
import com.example.kotlin_json.Singleton.VolleySingleton
import com.example.kotlin_json.ViewHolder.PokedexViewHolder
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IProfile
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {


    var pokedexlist = ArrayList<Pokedex>()
    lateinit var recyclerView: RecyclerView
    var url: String = Constant.BASE_URL
    lateinit var madapter: PokedexViewHolder



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        builddrawer()

        recyclerView = Recycler_pokemon
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)


        fab.setOnClickListener {
            var intent = Intent(this@MainActivity, AddPokemonActivity::class.java)
            startActivity(intent)
        }

        TxtSearch.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if (TxtSearch.text.isEmpty()) {
                    pokedexlist.clear()
                    parseJSON()
                } else {
                    pokedexlist.clear()
                    SearchJSON(TxtSearch.text.toString())
                }
                return@OnKeyListener true
            }
            false
        })
    }

    private fun builddrawer() {
        val headerResult = AccountHeaderBuilder()
            .withActivity(this)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                ProfileDrawerItem().withName("SpaceColony").withEmail("spacecolony@gmail.com").withIcon(
                    resources.getDrawable(
                        R.drawable.pokeball
                    )
                )
            )
            .withOnAccountHeaderListener { _, _, _ -> false }
            .build()

        val item2 = PrimaryDrawerItem().withIdentifier(2).withName("Practice Upload")


        val result = DrawerBuilder()
            .withActivity(this)
            .withAccountHeader(headerResult)
            .withToolbar(toolbarMain)
            .addDrawerItems(
                DividerDrawerItem(),
                item2
            )
            .withOnDrawerItemClickListener { _, _, drawerItem ->
                if (drawerItem != null) {
                    var intent: Intent? = null
                    if (drawerItem.identifier == 2L) {
                        intent = Intent(this@MainActivity, UploadFilePracticeActivity::class.java)
                    }
                    if (intent != null) {
                        this@MainActivity.startActivity(intent)
                    }
                }
                false
            }
            .build()
    }

    override fun onResume() {
        super.onResume()
        pokedexlist.clear()
        parseJSON()
    }


    private fun parseJSON() {
        val loading = ProgressDialog(this)
        loading.setMessage("Loading pokedex...")
        loading.show()
        loading.setCanceledOnTouchOutside(false)
        loading.setCancelable(false)

        val queue = VolleySingleton.getInstance(this.applicationContext).requestQueue


        val arrayRequest = JsonArrayRequest(Request.Method.GET, url + "pokedex/get_pokedex", null, Response.Listener<JSONArray>
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
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            })

        arrayRequest.retryPolicy = DefaultRetryPolicy(
            60000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        VolleySingleton.getInstance(this).addToRequestQueue(arrayRequest)
        queue.addRequestFinishedListener(RequestQueue.RequestFinishedListener<String> {
            madapter = PokedexViewHolder(pokedexlist)
            recyclerView.adapter = madapter
            loading.dismiss()
        })
    }

    private fun SearchJSON(keywords: String) {
        val queue = Volley.newRequestQueue(this)
        //val url = "http://192.168.2.94:3000/pokemon"

        val arrayRequest = JsonArrayRequest(Request.Method.GET, url + "pokedex/get_pokedex", null, Response.Listener<JSONArray>
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
                if (name.toLowerCase().contains(keywords)) {
                    pokedexlist.add(Pokedex(dexno, name, pokeimg, type))

                } else {
                    if (k == response.length() - 1) {
                        Toast.makeText(this, "Pokemon not found.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        },
            Response.ErrorListener
            { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            })
        queue.add(arrayRequest)
        queue.addRequestFinishedListener(RequestQueue.RequestFinishedListener<String> {
            recyclerView.adapter = PokedexViewHolder(pokedexlist)
        })
    }
}
