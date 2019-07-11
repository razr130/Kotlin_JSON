package com.example.kotlin_json.ui.login

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.example.kotlin_json.R
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)



            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginjson(username.text.toString(), password.text.toString())
            }
        }

    private fun loginjson(nik: String, password: String) {

        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.72.58:9080/user/login"

        var ob = JSONObject()
        ob.put("nik",nik)
        ob.put("password",password)
        var ob2 = JSONObject()
        ob2.put("json",ob)

        val jsonObjectRequest =  JsonObjectRequest(Request.Method.POST,url,ob2, Response.Listener {
                response ->
            Toast.makeText(this, response.getString("success"), Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener {
                error ->
            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
        })
        queue.add(jsonObjectRequest)

    }
}


