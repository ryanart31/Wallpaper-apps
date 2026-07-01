package com.example.wallpaper.network

import com.example.wallpaper.data.Wallpaper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WallpaperService {

    @GET("wallpapers")
    suspend fun getWallpapers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): List<Wallpaper>

    @GET("wallpapers/daily")
    suspend fun getDailyWallpaper(): Wallpaper

    @GET("wallpapers/{id}")
    suspend fun getWallpaperById(@Path("id") id: String): Wallpaper

    @GET("wallpapers/category/{category}")
    suspend fun getWallpapersByCategory(
        @Path("category") category: String,
        @Query("page") page: Int = 1
    ): List<Wallpaper>
}
