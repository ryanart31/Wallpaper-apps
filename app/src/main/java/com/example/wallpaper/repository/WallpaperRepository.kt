package com.example.wallpaper.repository

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import com.example.wallpaper.data.Wallpaper
import com.example.wallpaper.data.WallpaperDao
import com.example.wallpaper.network.WallpaperService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

class WallpaperRepository @Inject constructor(
    private val service: WallpaperService,
    private val dao: WallpaperDao,
    @ApplicationContext private val context: Context
) {

    suspend fun getWallpapers(): List<Wallpaper> = withContext(Dispatchers.IO) {
        return@withContext try {
            val wallpapers = service.getWallpapers()
            dao.insertWallpapers(wallpapers)
            wallpapers
        } catch (e: Exception) {
            dao.getAllWallpapers()
        }
    }

    suspend fun getDailyWallpaper(): Wallpaper = withContext(Dispatchers.IO) {
        return@withContext try {
            service.getDailyWallpaper()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getWallpaperById(id: String): Wallpaper? = withContext(Dispatchers.IO) {
        return@withContext try {
            service.getWallpaperById(id)
        } catch (e: Exception) {
            dao.getWallpaperById(id)
        }
    }

    suspend fun setAsWallpaper(wallpaper: Wallpaper) = withContext(Dispatchers.Main) {
        try {
            val bitmap = withContext(Dispatchers.IO) {
                val url = URL(wallpaper.imageUrl)
                url.openStream().use { BitmapFactory.decodeStream(it) }
            }
            val wallpaperManager = WallpaperManager.getInstance(context)
            wallpaperManager.setBitmap(bitmap)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun toggleFavorite(wallpaper: Wallpaper) = withContext(Dispatchers.IO) {
        dao.updateWallpaper(wallpaper.copy(isFavorite = !wallpaper.isFavorite))
    }

    suspend fun getFavorites(): List<Wallpaper> = withContext(Dispatchers.IO) {
        return@withContext dao.getFavoriteWallpapers()
    }
}
