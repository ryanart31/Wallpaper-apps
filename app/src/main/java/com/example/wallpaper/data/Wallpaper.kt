package com.example.wallpaper.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "wallpapers")
data class Wallpaper(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String? = null,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    @SerializedName("created_at")
    val createdAt: String,
    val category: String? = null,
    val resolution: String? = null,
    val isFavorite: Boolean = false
)
