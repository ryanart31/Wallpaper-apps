package com.example.wallpaper.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaper.data.Wallpaper
import com.example.wallpaper.repository.WallpaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WallpaperRepository
) : ViewModel() {

    private val _wallpapers = MutableLiveData<List<Wallpaper>>()
    val wallpapers: LiveData<List<Wallpaper>> = _wallpapers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadWallpapers()
    }

    fun loadWallpapers() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = repository.getWallpapers()
                _wallpapers.value = result
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectWallpaper(wallpaper: Wallpaper) {
        viewModelScope.launch {
            try {
                repository.setAsWallpaper(wallpaper)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to set wallpaper: ${e.message}"
            }
        }
    }
}
