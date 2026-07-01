package com.example.wallpaper.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WallpaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallpaper(wallpaper: Wallpaper)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallpapers(wallpapers: List<Wallpaper>)

    @Update
    suspend fun updateWallpaper(wallpaper: Wallpaper)

    @Delete
    suspend fun deleteWallpaper(wallpaper: Wallpaper)

    @Query("SELECT * FROM wallpapers ORDER BY created_at DESC")
    suspend fun getAllWallpapers(): List<Wallpaper>

    @Query("SELECT * FROM wallpapers WHERE isFavorite = 1 ORDER BY created_at DESC")
    suspend fun getFavoriteWallpapers(): List<Wallpaper>

    @Query("SELECT * FROM wallpapers WHERE id = :id")
    suspend fun getWallpaperById(id: String): Wallpaper?

    @Query("SELECT * FROM wallpapers WHERE category = :category ORDER BY created_at DESC")
    suspend fun getWallpapersByCategory(category: String): List<Wallpaper>

    @Query("DELETE FROM wallpapers WHERE id = :id")
    suspend fun deleteWallpaperById(id: String)
}
