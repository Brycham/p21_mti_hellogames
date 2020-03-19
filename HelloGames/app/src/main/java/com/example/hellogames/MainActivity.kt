package com.example.hellogames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // A List to store or objects
        val data = arrayListOf<GameDetails>()
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

        val explicitIntent = Intent(this, DetailsActivity::class.java)

        val wsCallback: Callback<List<GameDetails>> = object : Callback<List<GameDetails>> {
            override fun onFailure(call: Call<List<GameDetails>>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<List<GameDetails>>, response:
            Response<List<GameDetails>>
            ) {
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        val int1 = Random.nextInt(responseData.size)
                        val int2 = Random.nextInt(responseData.size)
                        val int3 = Random.nextInt(responseData.size)
                        val int4 = Random.nextInt(responseData.size)
                        Glide
                            .with(applicationContext)
                            .load(responseData[int1].picture)
                            .into(imageView0_0)

                        imageView0_0.setOnClickListener {
                            explicitIntent.putExtra("ID", responseData[int1].id)
                            startActivity(explicitIntent)
                        }

                        Glide
                                .with(applicationContext)
                                .load(responseData[int2].picture)
                                .into(imageView0_1)

                        imageView0_1.setOnClickListener {
                            explicitIntent.putExtra("ID", responseData[int2].id)
                            startActivity(explicitIntent)
                        }

                        Glide
                                .with(applicationContext)
                                .load(responseData[int3].picture)
                                .into(imageView1_0)

                        imageView1_0.setOnClickListener {
                            explicitIntent.putExtra("ID", responseData[int3].id)
                            startActivity(explicitIntent)
                        }

                        Glide
                                .with(applicationContext)
                                .load(responseData[int4].picture)
                                .into(imageView1_1)

                        imageView1_1.setOnClickListener {
                            explicitIntent.putExtra("ID", responseData[int4].id)
                            startActivity(explicitIntent)
                        }
                        Log.d("TAG", "WebService success : " + responseData.size)
                    }
                }
            }
        }
        service.getGames().enqueue(wsCallback)
    }
}
