package com.example.kotlin_json

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.kotlin_json.CustomRequest.VolleyMultipartRequest
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.activity_add_pokemon.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddPokemonActivity : AppCompatActivity() {

    var type = arrayListOf<String>()
    val url = "http://192.168.2.184:9090/PostPokedex/post_dex"
    private var rQueue: RequestQueue? = null
    lateinit var bitmap: Bitmap
    var bitmaparray:ArrayList<Bitmap> = ArrayList()
    var datapartarray: ArrayList<VolleyMultipartRequest.DataPart> = ArrayList()

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

        PicPokemonAdd.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 0)
        }

        BtnAdd.setOnClickListener {
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

    var selectorphotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
//            selectorphotoUri = data.data
//            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectorphotoUri)
//            PicPokemonAdd.setImageBitmap(bitmap)
//
//        }
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

        var ob = JSONObject()
        ob.put("pokedex_id", id)
        ob.put("pokemon_name", name)
        //ob.put("type", ar)
        ob.put("species", species)
        ob.put("height", height)
        ob.put("weight", weight)
        ob.put("image", name + ".jpeg")
        ob.put("abilities", abilities)

        var ob2 = JSONObject()
        ob2.put("hp", hp)
        ob2.put("attack", attack)
        ob2.put("defense", defense)
        ob2.put("spattack", spattack)
        ob2.put("spdefense", spdefense)
        ob2.put("speed", speed)

        var ar = JSONArray()
        var ob3 = JSONObject()

        for (i in 0 until type.size) {
            ob3.put("type1", type.get(i))
            ar.put(ob3)
        }
        //ob.put("stats",ob2)


        val volleyMultipartRequest = object : VolleyMultipartRequest(Request.Method.POST, url,
            Response.Listener { response ->

                rQueue!!.cache.clear()
                try {
                    val jsonObject = JSONObject(String(response.data))

                    if (jsonObject.getString("success") == "1") {
                        Toast.makeText(
                            applicationContext,
                            "success : " + jsonObject.getString("success"),
                            Toast.LENGTH_LONG
                        ).show()
                        Toast.makeText(applicationContext, jsonObject.getString("token"), Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "success : " + jsonObject.getString("success"),
                            Toast.LENGTH_LONG
                        ).show()
                        Toast.makeText(applicationContext, jsonObject.getString("token"), Toast.LENGTH_LONG).show()
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
                val params = HashMap<String,  ArrayList<DataPart>>()

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
        rQueue = Volley.newRequestQueue(this@AddPokemonActivity)
        rQueue!!.add(volleyMultipartRequest)
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
        finish()
    }

    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}
