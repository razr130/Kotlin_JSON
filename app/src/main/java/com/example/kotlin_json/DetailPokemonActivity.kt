package com.example.kotlin_json

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.kotlin_json.Model.Pokedex
import com.example.kotlin_json.ViewHolder.PokedexViewHolder
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_pokemon.*
import org.json.JSONArray
import org.json.JSONObject
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler


class DetailPokemonActivity : AppCompatActivity() {
    lateinit var TheChart : HorizontalBarChart
    var url = Constant.BASE_URL
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pokemon)

        var rec_id = intent.getStringExtra("id").toString()

        parseJSON(rec_id)


    }


    private fun parseJSON(id: String) {
        val loading = ProgressDialog(this)
        loading.setMessage("Loading pokedex...")
        loading.show()
        loading.setCanceledOnTouchOutside(false)
        loading.setCancelable(false)
        val queue = Volley.newRequestQueue(this)
        val imgurl = url + "Content/Images/"

        val arrayRequest = JsonObjectRequest(
            Request.Method.GET, url + "pokedex/get_pokedex?id=" + id, null, Response.Listener<JSONObject>
            { response ->

                    val ob: JSONObject = response

                    val dexno: String = ob.getString("pokedex_id")
                    val name: String = ob.getString("pokemon_name")
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
                            else if(ob.getJSONArray("type").getString(i) == ""){
                                IconType2Detail.visibility = View.GONE
                            }
                        }
                    }

                    val statarray = ob.getJSONObject("stat")
                    var hp = statarray.getInt("hp")
                    var attack = statarray.getInt("attack")
                    var defense = statarray.getInt("defense")
                    var spattack = statarray.getInt("spattack")
                    var spdefense = statarray.getInt("spdefense")
                    var speed = statarray.getInt("speed")

                    TxtNameDetail.text = name
                    TxtDexNoDetailData.text = dexno
                    TxtSpeciesDetailData.text = species
                    TxtHeightDetailData.text = height
                    TxtWeightDetailData.text = weight
                    TxtAbilitiesDetailData.text = abilities
                    if(pokeimg == ""){
                        val context: Context = ImagePokemonDetail.getContext()
                        var id = context.resources.getIdentifier("pokeball", "drawable", context.packageName)
                        ImagePokemonDetail.setImageResource(id)
                    }
                    else{
                        Picasso.with(this).load(imgurl + pokeimg).into(ImagePokemonDetail)
                    }
//                    setupstatchart(hp,attack,defense,spattack,spdefense,speed)
                    setStatGraph(hp,attack,defense,spattack,spdefense,speed)

                    loading.dismiss()
            },
            Response.ErrorListener
            { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            })
        queue.add(arrayRequest)
    }

    private fun setStatGraph(hp: Int, attack: Int, defense: Int, spattack: Int, spdefense: Int, speed: Int) {

        TheChart = StatChart

        TheChart.setDrawBarShadow(false)
        val description = Description()
        description.text = ""
        TheChart.description = description
        TheChart.legend.setEnabled(false)
        TheChart.setPinchZoom(false)
        TheChart.setDrawValueAboveBar(false)
        TheChart.setScaleEnabled(false)

        val xAxis = TheChart.getXAxis()
        xAxis.setDrawGridLines(false)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setEnabled(true)
        xAxis.setDrawAxisLine(false)


        val yLeft = TheChart.axisLeft

        yLeft.axisMaximum = 250f
        yLeft.axisMinimum = 0f
        yLeft.isEnabled = false

        xAxis.setLabelCount(6)

        val values = arrayOf("Speed", "Sp.Defense", "Sp.Atack", "Defense", "Attack","HP")
        xAxis.valueFormatter = XAxisValueFormatter(values)

        val yRight = TheChart.axisRight
        yRight.setDrawAxisLine(true)
        yRight.setDrawGridLines(false)
        yRight.isEnabled = false

        setGraphData(hp,attack,defense,spattack,spdefense,speed)

        TheChart.animateY(2000)
    }

    inner class XAxisValueFormatter(private val values: Array<String>) : IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase): String {
            return this.values[value.toInt()]
        }
    }

    private fun setGraphData(hp: Int, attack: Int, defense: Int, spattack: Int, spdefense: Int, speed: Int) {

        //Add a list of bar entries
        val colors = java.util.ArrayList<Int>()
        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(5f, (hp).toFloat()))
        if(hp<=50){
            colors.add(Color.rgb(255,68,34))
        }
        else if(hp in 51..80){
            colors.add(Color.rgb(255,204,51))
        }
        else if(hp in 81..200){
            colors.add(Color.rgb(119,204,85))
        }
        else{
            colors.add(Color.rgb(102,204,255))
        }
        entries.add(BarEntry(4f, (attack).toFloat()))
        if(attack<=50){
            colors.add(Color.rgb(255,68,34))
        }
        else if(attack in 51..80){
            colors.add(Color.rgb(255,204,51))
        }
        else if(attack in 81..200){
            colors.add(Color.rgb(119,204,85))
        }
        else{
            colors.add(Color.rgb(102,204,255))
        }
        entries.add(BarEntry(3f, (defense).toFloat()))
        if(defense<=50){
            colors.add(Color.rgb(255,68,34))
        }
        else if(defense in 51..80){
            colors.add(Color.rgb(255,204,51))
        }
        else if(defense in 81..200){
            colors.add(Color.rgb(119,204,85))
        }
        else{
            colors.add(Color.rgb(102,204,255))
        }
        entries.add(BarEntry(2f, (spattack).toFloat()))
        if(spattack<=50){
            colors.add(Color.rgb(255,68,34))
        }
        else if(spattack in 51..80){
            colors.add(Color.rgb(255,204,51))
        }
        else if(spattack in 81..200){
            colors.add(Color.rgb(119,204,85))
        }
        else{
            colors.add(Color.rgb(102,204,255))
        }
        entries.add(BarEntry(1f, (spdefense).toFloat()))
        if(spdefense<=50){
            colors.add(Color.rgb(255,68,34))
        }
        else if(spdefense in 51..80){
            colors.add(Color.rgb(255,204,51))
        }
        else if(spdefense in 81..200){
            colors.add(Color.rgb(119,204,85))
        }
        else{
            colors.add(Color.rgb(102,204,255))
        }
        entries.add(BarEntry(0f, (speed).toFloat()))
        if(speed<=50){
            colors.add(Color.rgb(255,68,34))
        }
        else if(speed in 51..80){
            colors.add(Color.rgb(255,204,51))
        }
        else if(speed in 81..200){
            colors.add(Color.rgb(119,204,85))
        }
        else{
            colors.add(Color.rgb(102,204,255))
        }
        val barDataSet = BarDataSet(entries, "Bar Data Set")

        barDataSet.colors = colors

        TheChart.setDrawBarShadow(true)
        barDataSet.barShadowColor = Color.argb(40, 150, 150, 150)
        val data = BarData(barDataSet)

        data.barWidth = 0.7f

        TheChart.data = data
        TheChart.invalidate()
    }

}
