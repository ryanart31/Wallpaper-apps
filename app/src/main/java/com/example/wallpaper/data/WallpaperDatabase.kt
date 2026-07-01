package com.example.wallpaper.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Wallpaper::class], version = 1, exportSchema = false)
abstract class WallpaperDatabase : RoomDatabase() {
    abstract fun wallpaperDao(): WallpaperDao
}
