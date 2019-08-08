package com.example.kotlin_json.Model

import java.io.Serializable

data class Pokedex(var id: String, var name: String, var image:String, var type:String, var isanswer:Boolean=false) : Serializable

//    var id: String = ""
//    var name: String = ""
//    var image: String = ""
//    val type: List<String> = ArrayList()


