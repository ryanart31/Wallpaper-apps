package com.example.wallpaper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import com.example.wallpaper.databinding.ActivityMainBinding
import com.example.wallpaper.ui.WallpaperAdapter
import com.example.wallpaper.ui.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: WallpaperAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        // Setup RecyclerView
        adapter = WallpaperAdapter { wallpaper ->
            viewModel.selectWallpaper(wallpaper)
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = this@MainActivity.adapter
        }

        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Wallpapers"

        // Setup FAB for settings
        binding.fab.setOnClickListener {
            // Open settings
        }
    }

    private fun observeViewModel() {
        viewModel.wallpapers.observe(this) { wallpapers ->
            adapter.submitList(wallpapers)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
        }

        viewModel.error.observe(this) { error ->
            if (error != null) {
                showError(error)
            }
        }
    }

    private fun showError(message: String) {
        com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            message,
            com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
        ).show()
    }
}
