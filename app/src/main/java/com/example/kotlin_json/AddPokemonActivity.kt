package com.example.kotlin_json

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.kotlin_json.CustomRequest.VolleyMultipartRequest
import com.example.kotlin_json.Singleton.VolleySingleton
import kotlinx.android.synthetic.main.activity_add_pokemon.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class AddPokemonActivity : AppCompatActivity() {

    var type = arrayListOf<String>()
    val url = Constant.BASE_URL
    private var rQueue: RequestQueue? = null
    lateinit var bitmap: Bitmap
    var bitmaparray: ArrayList<Bitmap> = ArrayList()
    var datapartarray: ArrayList<VolleyMultipartRequest.DataPart> = ArrayList()
    var typearray = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pokemon)

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)


        loadtype()
        PicPokemonAdd.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 0)
        }

        BtnAdd.setOnClickListener {

            if (TextUtils.isEmpty(TxtPokedexNoAdd.text)) {
                TxtPokedexNoAdd.error = "Pokedex ID is empty"
                TxtPokedexNoAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonNameAdd.text)) {
                TxtPokemonNameAdd.error = "Pokedex Name is empty"
                TxtPokemonNameAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonSpeciesAdd.text)) {
                TxtPokemonSpeciesAdd.error = "Species is empty"
                TxtPokemonSpeciesAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonHeightAdd.text)) {
                TxtPokemonHeightAdd.error = "Height is empty"
                TxtPokemonHeightAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonWeightAdd.text)) {
                TxtPokemonWeightAdd.error = "Weight is empty"
                TxtPokemonWeightAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonAbilitiesAdd.text)) {
                TxtPokemonAbilitiesAdd.error = "Abilities is empty"
                TxtPokemonAbilitiesAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonHPAdd.text)) {
                TxtPokemonHPAdd.error = "HP is empty"
                TxtPokemonHPAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonAttackAdd.text)) {
                TxtPokemonAttackAdd.error = "Attack is empty"
                TxtPokemonAttackAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonDefenseAdd.text)) {
                TxtPokemonDefenseAdd.error = "Defense is empty"
                TxtPokemonDefenseAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonSpAttackAdd.text)) {
                TxtPokemonSpAttackAdd.error = "Sp. Attack is empty"
                TxtPokemonSpAttackAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonSpDefenseAdd.text)) {
                TxtPokemonSpDefenseAdd.error = "Sp. Defense is empty"
                TxtPokemonSpDefenseAdd.requestFocus()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(TxtPokemonSpeedAdd.text)) {
                TxtPokemonSpeedAdd.error = "Speed is empty"
                TxtPokemonSpeedAdd.requestFocus()
                return@setOnClickListener
            }

            upload(
                TxtPokedexNoAdd.text.toString(),
                TxtPokemonNameAdd.text.toString(),
                type,
                TxtPokemonSpeciesAdd.text.toString(),
                (TxtPokemonHeightAdd.text.toString()).toDouble(),
                (TxtPokemonWeightAdd.text.toString()).toDouble(),
                TxtPokemonAbilitiesAdd.text.toString(),
                (TxtPokemonHPAdd.text.toString()).toInt(),
                (TxtPokemonAttackAdd.text.toString()).toInt(),
                (TxtPokemonDefenseAdd.text.toString()).toInt(),
                (TxtPokemonSpAttackAdd.text.toString()).toInt(),
                (TxtPokemonSpDefenseAdd.text.toString()).toInt(),
                (TxtPokemonSpeedAdd.text.toString()).toInt()
            )

        }

    }

    private fun loadtype() {
        val queue = Volley.newRequestQueue(this)
        val arrayRequest =
            JsonArrayRequest(Request.Method.GET, url + "type/get_type", null, Response.Listener<JSONArray>
            { response ->
                for (k in 0 until response.length()) {
                    val ob: JSONObject = response.getJSONObject(k)

                    val type_name: String = ob.getString("type_name")
                    typearray.add(type_name)

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
        queue.add(arrayRequest)
        queue.addRequestFinishedListener(RequestQueue.RequestFinishedListener<String> {
            loadspinner()
        })
    }

    private fun loadspinner() {

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, typearray)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddPokemonActivity, "Select some type please", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (parent?.getItemAtPosition(position)!! == "none") {

                } else {
                    if (type.size == 0) {
                        type.add(parent.getItemAtPosition(position).toString())
                        Toast.makeText(this@AddPokemonActivity, type[0], Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        type[0] = parent.getItemAtPosition(position).toString()
                        Toast.makeText(this@AddPokemonActivity, type[0], Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            }
        }

        spinner2.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, typearray)

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@AddPokemonActivity, "Select some type please", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (parent?.getItemAtPosition(position)!! == "none") {
                } else {

                    if (type.size == 1) {
                        type.add(parent.getItemAtPosition(position).toString())
                        Toast.makeText(this@AddPokemonActivity, type[1], Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        type[1] = parent.getItemAtPosition(position).toString()
                        Toast.makeText(this@AddPokemonActivity, type[1], Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val clipdata = data?.clipData

        val contentURI = data?.data
        try {

            if (clipdata == null) {
                bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(contentURI))
                PicPokemonAdd.setImageBitmap(bitmap)
                bitmaparray.add(bitmap)
            } else {
                Toast.makeText(this@AddPokemonActivity, "Multiple file selected!", Toast.LENGTH_SHORT)
                    .show()
                for (i in 0 until clipdata.itemCount) {
                    val uri = clipdata.getItemAt(i).uri
                    //bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                    bitmaparray.add(BitmapFactory.decodeStream(contentResolver.openInputStream(uri)))
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this@AddPokemonActivity, "Failed!", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("InflateParams")
    private fun upload(
        id: String?,
        name: String?,
        type: ArrayList<String>,
        species: String?,
        height: Double?,
        weight: Double?,
        abilities: String?,
        hp: Int?,
        attack: Int?,
        defense: Int?,
        spattack: Int?,
        spdefense: Int?,
        speed: Int?
    ) {
        val loading = ProgressDialog(this)
        loading.setMessage("Adding dex data...")
        loading.show()
        loading.setCanceledOnTouchOutside(false)
        loading.setCancelable(false)
        val ob = JSONObject()
        ob.put("pokedex_id", id)
        ob.put("pokemon_name", name)
        //ob.put("type", ar)
        ob.put("species", species)
        ob.put("height", height)
        ob.put("weight", weight)
        ob.put("image", name + ".jpeg")
        ob.put("abilities", abilities)

        val ob2 = JSONObject()
        ob2.put("hp", hp)
        ob2.put("attack", attack)
        ob2.put("defense", defense)
        ob2.put("spattack", spattack)
        ob2.put("spdefense", spdefense)
        ob2.put("speed", speed)

        val ar = JSONArray()


        for (i in 0 until type.size) {
            val ob3 = JSONObject()
            ob3.put("type1", type.get(i))
            ar.put(ob3)
        }
        //ob.put("stats",ob2)
        val builder = AlertDialog.Builder(this)
        val dialogview = layoutInflater.inflate(R.layout.progress_dialogue, null)
        val message = dialogview.findViewById<TextView>(R.id.TxtProgressMessage)
        message.text = "Uploading data ... "
        builder.setView(dialogview)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()

        val volleyMultipartRequest = object : VolleyMultipartRequest(Method.POST, url + "PostPokedex/post_dex",
            Response.Listener { response ->


                try {
                    val jsonObject = JSONObject(String(response.data))

                    if (jsonObject.getString("success") == "1") {

                        loading.dismiss()
                        this.finish()

                    } else {

                        Toast.makeText(applicationContext, jsonObject.getString("token"), Toast.LENGTH_LONG).show()
                        this.finish()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {


            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("dexdata", ob.toString())
                params.put("statdata", ob2.toString())
                params.put("typedata", ar.toString())
                return params
            }

            override fun getByteDataMultiple(): HashMap<String, ArrayList<DataPart>> {
                val params = HashMap<String, ArrayList<DataPart>>()

                for (i in 0 until bitmaparray.size) {
                    val imagename: String = name + i.toString()
                    datapartarray.add(DataPart("$imagename.jpeg", getFileDataFromDrawable(bitmaparray[i])))
                }
                params.put("image", datapartarray)
                return params
            }
        }


        volleyMultipartRequest.retryPolicy = DefaultRetryPolicy(
            30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        VolleySingleton.getInstance(this).addToRequestQueue(volleyMultipartRequest)
        dialog.dismiss()
    }

    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}
