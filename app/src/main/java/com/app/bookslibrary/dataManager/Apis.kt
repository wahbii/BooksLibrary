package com.app.bookslibrary.dataManager

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface Apis {
    @GET("search.php")
    suspend fun search(@Query("req") name :String): Response<String>

    @GET
    suspend fun getImageURL(@Url url: Url):Response<String>


}