package com.example.wallpaper.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wallpaper.data.Wallpaper
import com.example.wallpaper.databinding.ItemWallpaperBinding

class WallpaperAdapter(
    private val onWallpaperClick: (Wallpaper) -> Unit
) : ListAdapter<Wallpaper, WallpaperAdapter.WallpaperViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val binding = ItemWallpaperBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WallpaperViewHolder(binding, onWallpaperClick)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class WallpaperViewHolder(
        private val binding: ItemWallpaperBinding,
        private val onWallpaperClick: (Wallpaper) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(wallpaper: Wallpaper) {
            binding.apply {
                Glide.with(root)
                    .load(wallpaper.thumbnailUrl)
                    .into(wallpaperImage)

                wallpaperTitle.text = wallpaper.title

                root.setOnClickListener {
                    onWallpaperClick(wallpaper)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Wallpaper>() {
            override fun areItemsTheSame(oldItem: Wallpaper, newItem: Wallpaper) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Wallpaper, newItem: Wallpaper) =
                oldItem == newItem
        }
    }
}
