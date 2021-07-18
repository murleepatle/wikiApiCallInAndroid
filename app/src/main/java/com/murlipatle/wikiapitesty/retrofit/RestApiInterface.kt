package com.murlipatle.wikiapitesty.retrofit

import com.murlipatle.wikiapitesty.retrofit.responce.CategoryResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RestApiInterface {



    @GET("api.php")
    suspend fun getAllCategoryList(
        @Query("action") action: String,
        @Query("list") list: String,
        @Query("acprefix") acprefix: String,
        @Query("formatversion") formatversion: Int,
        @Query("format") format: String,
        @Query("accontinue") continueV: String?
    ): Response<CategoryResponse>

  @GET("api.php")
    suspend fun getRandomArticle(
      @Query("format") format: String,
        @Query("action") action: String,
        @Query("generator") generator: String,
        @Query("grnnamespace") grnnamespace: String,
        @Query("prop",encoded = true) prop: String,
        @Query("rvprop") rvprop: String,
        @Query("grnlimit") grnlimit: String,
    ): Response<ResponseBody>

    @GET
    suspend fun getImagesList(
        @Url  url:String,
        @Query("action") action: String,
        @Query("prop") prop: String,
        @Query("generator") generator: String,
        @Query("gcmtype") gcmtype: String,
        @Query("gcmtitle",encoded = false) gcmtitle: String,
        @Query("format") format: String,
        @Query("gcmcontinue") gcmcontinue: String?,
        @Query("iiprop",encoded = true) iiprop: String


        ): Response<ResponseBody>


}