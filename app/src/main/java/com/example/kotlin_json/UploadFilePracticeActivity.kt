package com.example.kotlin_json

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.Volley
import com.example.kotlin_json.CustomRequest.VolleyMultipartRequest
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.DexterError
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_upload_file_practice.*

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.List
import kotlin.collections.ArrayList
import kotlin.collections.Map


class UploadFilePracticeActivity : AppCompatActivity() {

    val url: String = "http://192.168.2.184:9090/PostPokedex/upload_file"
    var GALLERY: Int = 1
    private var rQueue: RequestQueue? = null
    private var arraylist: ArrayList<HashMap<String, String>>? = null
    lateinit var bitmap: Bitmap
    var bitmaparray: ArrayList<Bitmap> = ArrayList()
    private val REQUEST_PICK_PHOTO = 1
    var multiple = true
    var datapartarray: ArrayList<VolleyMultipartRequest.DataPart> = ArrayList()
    //val progressdialog = ProgressDialog(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_file_practice)

        //progressdialog.setMessage("uploading...")
        requestMultiplePermissions()

        BtnSelectImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_PHOTO)
        }

        BtnUpload.setOnClickListener {
            //progressdialog.show()

            if(multiple == true){
                uploadImagemultiple(bitmaparray)
            }
            else{
                uploadImage(bitmap)
            }

        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 0) {
            return
        }
        if (requestCode == GALLERY) {
            val clipdata = data?.clipData

            if (clipdata == null) {
                multiple = false
                val contentURI = data?.data
                try {

                    //bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(contentURI))
                    ImagePreview.visibility = View.VISIBLE
                    BtnUpload.visibility = View.VISIBLE
                    ImagePreview.setImageBitmap(bitmap)


                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@UploadFilePracticeActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }
            else
            {

                multiple = true
                ImagePreview.visibility = View.GONE
                BtnUpload.visibility = View.VISIBLE
                Toast.makeText(this@UploadFilePracticeActivity, "Multiple file selected!", Toast.LENGTH_SHORT).show()
                for(i in 0 until clipdata.itemCount)
                {
                    val uri = clipdata.getItemAt(i).uri
                    //bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                    bitmaparray.add(BitmapFactory.decodeStream(contentResolver.openInputStream(uri)))
                }

            }
        }
    }

    private fun uploadImage(bitmap: Bitmap) {

        val volleyMultipartRequest = object : VolleyMultipartRequest(Request.Method.POST, url,
            Response.Listener { response ->

                rQueue!!.cache.clear()
                try {
                    val jsonObject = JSONObject(String(response.data))

                    if (jsonObject.getString("success") == "1") {
                        Toast.makeText(applicationContext,"success : " + jsonObject.getString("success"), Toast.LENGTH_LONG).show()
                        Toast.makeText(applicationContext, jsonObject.getString("token"), Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(applicationContext,"success : " + jsonObject.getString("success"), Toast.LENGTH_LONG).show()
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
            override fun getParams(): kotlin.collections.Map<String, String> {
                return HashMap()
            }

            /*
             *pass files using below method
             * */
            override fun getByteData(): Map<String, DataPart> {
                val params = HashMap<String, DataPart>()
                val date = Date()
                val formatter = SimpleDateFormat("dd-mm-yyyy-HH-mma")
                val imagename:  String = formatter.format(date)
                params.put("image", DataPart("$imagename.jpeg", getFileDataFromDrawable(bitmap)))
                return params
            }
        }


        volleyMultipartRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        rQueue = Volley.newRequestQueue(this@UploadFilePracticeActivity)
        rQueue!!.add(volleyMultipartRequest)
       // rQueue!!.addRequestFinishedListener(RequestQueue.RequestFinishedListener<String> {
//            if (progressdialog != null && progressdialog.isShowing())
//                progressdialog.dismiss()
//        })
    }

    private fun uploadImagemultiple(bitmap: ArrayList<Bitmap>) {

        val volleyMultipartRequest = object : VolleyMultipartRequest(Request.Method.POST, url,
            Response.Listener { response ->

                rQueue!!.cache.clear()
                try {
                    val jsonObject = JSONObject(String(response.data))

                    if (jsonObject.getString("success") == "1") {
                        Toast.makeText(applicationContext,"success : " + jsonObject.getString("success"), Toast.LENGTH_LONG).show()
                        Toast.makeText(applicationContext, jsonObject.getString("token"), Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(applicationContext,"success : " + jsonObject.getString("success"), Toast.LENGTH_LONG).show()
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
            override fun getParams(): kotlin.collections.Map<String, String> {
                return HashMap()
            }

            /*
             *pass files using below method
             * */
            override fun getByteDataMultiple(): HashMap<String, ArrayList<DataPart>> {
                val params = HashMap<String, ArrayList<DataPart>>()
                val date = Date()
                val formatter = SimpleDateFormat("dd-mm-yyyy-HH-mma")

                for(i in 0 until bitmap!!.size){
                val imagename:  String = formatter.format(date) + i.toString()
                datapartarray.add(DataPart("$imagename.jpeg", getFileDataFromDrawable(bitmap[i])))
                }
                params.put("image", datapartarray)
                return params
            }
        }


        volleyMultipartRequest.retryPolicy = DefaultRetryPolicy(
            0,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        rQueue = Volley.newRequestQueue(this@UploadFilePracticeActivity)
        rQueue!!.add(volleyMultipartRequest)
        rQueue!!.addRequestFinishedListener(RequestQueue.RequestFinishedListener<String> {
            bitmaparray.clear()
            datapartarray.clear()
        })
    }

    fun getFileDataFromDrawable(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    private fun requestMultiplePermissions() {
        Dexter.withActivity(this)
            .withPermissions(

                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    // check if all permissions are granted
                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(applicationContext, "All permissions are granted by user!", Toast.LENGTH_SHORT)
                            .show()
                    }

                    // check for permanent denial of any permission
                    if (report.isAnyPermissionPermanentlyDenied) {
                        // show alert dialog navigating to Settings

                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: kotlin.collections.List<PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener { Toast.makeText(applicationContext, "Some Error! ", Toast.LENGTH_SHORT).show() }
            .onSameThread()
            .check()
    }
}
