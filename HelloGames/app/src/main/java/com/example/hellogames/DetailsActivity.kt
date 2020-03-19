package com.example.hellogames

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        // A List to store or objects
        val data = arrayListOf<GameInfos>()
        // The base URL where the WebService is located
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

        val originalIntent = intent
        val id = originalIntent.getIntExtra("ID", 1)


        val wsCallback: Callback<GameInfos> = object : Callback<GameInfos> {
            override fun onFailure(call: Call<GameInfos>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<GameInfos>, response:
            Response<GameInfos>
            ) {
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        Glide
                            .with(applicationContext)
                            .load(responseData.picture)
                            .into(imageView_game)

                        textView_name.text = responseData.name
                        textView_type.text = responseData.type
                        textView_players.text = responseData.players.toString()
                        textView_year.text = responseData.year.toString()
                        textView_description.text = responseData.description_en

                        button_knowMore.setOnClickListener {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(responseData.url)))
                        }
                        Log.d("TAG", "WebService success")
                    }
                }
            }
        }
        service.getInfos(id).enqueue(wsCallback)
    }
}
