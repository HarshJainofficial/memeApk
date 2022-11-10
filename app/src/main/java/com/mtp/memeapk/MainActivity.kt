package com.mtp.memeapk

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

// import kotlinx.android.synthetic.main.activity_main.*

class  MainActivity : AppCompatActivity() {
    private var currentImageUrl: String ? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMore()
    }

    private fun loadMore(){

        val pro = findViewById<ProgressBar>(R.id.progressBar)
        val file = findViewById<ImageView>(R.id.imageView)

        pro.visibility = View.VISIBLE
//        nextButton(view = View).isEnabled = false
//        shareButton(view = View)isEnabled = false

         // val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")


                Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable> {

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pro.visibility = View.GONE
                        return  false
                        // TODO("Not yet implemented")
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pro.visibility = View.GONE
                        return false
                        //TODO("Not yet implemented")
                    }


                }).into(file)

            },
            {
              Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            })
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

// Add the request to the RequestQueue.

    //queue.add(jsonObjectRequest)

    }

    fun shareButton(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT , "Hey! , Checkout this cool meme I got from Reddit $currentImageUrl")
        val chooser = Intent.createChooser(intent, "Share this meme using...")
        startActivity(chooser)

    }
    fun nextButton(view: View) {
        loadMore()
    }
}