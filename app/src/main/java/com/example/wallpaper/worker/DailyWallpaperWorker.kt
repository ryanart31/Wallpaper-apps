package com.example.wallpaper.worker

import android.app.WallpaperManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.wallpaper.network.WallpaperService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DailyWallpaperWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @Inject
    lateinit var service: WallpaperService

    override suspend fun doWork(): Result {
        return try {
            val wallpaper = service.getDailyWallpaper()
            val bitmap = withContext(Dispatchers.IO) {
                URL(wallpaper.imageUrl).openStream().use {
                    BitmapFactory.decodeStream(it)
                }
            }
            val wallpaperManager = WallpaperManager.getInstance(applicationContext)
            wallpaperManager.setBitmap(bitmap)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        private const val DAILY_WALLPAPER_WORK = "daily_wallpaper_work"

        fun scheduleDailyWallpaper(context: Context) {
            val dailyWallpaperRequest = PeriodicWorkRequestBuilder<DailyWallpaperWorker>(
                1, TimeUnit.DAYS
            ).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                DAILY_WALLPAPER_WORK,
                ExistingPeriodicWorkPolicy.KEEP,
                dailyWallpaperRequest
            )
        }

        fun cancelDailyWallpaper(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(DAILY_WALLPAPER_WORK)
        }
    }
}
