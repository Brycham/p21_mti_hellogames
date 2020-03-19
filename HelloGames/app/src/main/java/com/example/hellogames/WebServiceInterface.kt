package com.example.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface {
    @GET("game/list")
    fun getGames(): Call<List<GameDetails>>

    @GET("game/details")
    fun getInfos(@Query("game_id") id: Int): Call<GameInfos>
}