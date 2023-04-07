package org.diyorbek.mvvm_h1.network

import org.diyorbek.mvvm_h1.model.PhotoItem
import org.diyorbek.mvvm_h1.model.PhotoResponse
import retrofit2.*
import retrofit2.http.*

interface ApiService {

    @Headers("Content-type:application/json")

    @GET("search?query=nature")
    fun getAllPhotos(): Call<PhotoResponse>

    @GET("photos/{id}")
    fun getOnePhoto(
        @Path("id") id: Int
    ): Call<PhotoItem>

    @GET("photos/15286")
    fun getOnePhotoThis(
    ): Call<PhotoItem>

    @GET("search")
    fun searchPhoto(
        @Query("query") query: String
    ): Call<PhotoResponse>
}