package com.example.kotlin_json.Fragments


import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.kotlin_json.Adapter.MoveViewHolder
import com.example.kotlin_json.Constant
import com.example.kotlin_json.Model.Move

import com.example.kotlin_json.R
import com.example.kotlin_json.Singleton.VolleySingleton
import kotlinx.android.synthetic.main.fragment_move_list.*
import org.json.JSONArray
import org.json.JSONObject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MoveListFragment : Fragment() {

    var movelist = ArrayList<Move>()
    lateinit var recyclerView: RecyclerView
    var url: String = Constant.BASE_URL
    lateinit var madapter: MoveViewHolder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_move_list, container, false)

    }


    override fun onResume() {
        super.onResume()
        movelist.clear()
        parseJSON()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = Recycler_move
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

    }

    private fun parseJSON() {
        val loading = ProgressDialog(activity)
        loading.setMessage("Loading move...")
        loading.show()
        loading.setCanceledOnTouchOutside(false)
        loading.setCancelable(false)

        val queue = VolleySingleton.getInstance(activity!!.applicationContext).requestQueue


        val arrayRequest = JsonArrayRequest(
            Request.Method.GET, url + "move/get_move", null, Response.Listener<JSONArray>
            { response ->
                for (k in 0 until response.length()) {
                    val ob: JSONObject = response.getJSONObject(k)

                    val moveid: Int = ob.getInt("move_id")
                    val name: String = ob.getString("move_name")
                    val category: String = ob.getString("move_category")
                    var type: String = ob.getString("move_type")
                    val dmg: Int = ob.getInt("move_damage")
                    val effect: String = ob.getString("move_effect")

                    movelist.add(Move(moveid, name, type, category,dmg,effect))
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
            madapter = MoveViewHolder(movelist)
            recyclerView.adapter = madapter
            loading.dismiss()
        })
    }
}
