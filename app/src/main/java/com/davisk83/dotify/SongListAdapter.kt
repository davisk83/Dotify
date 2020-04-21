package com.davisk83.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(songs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    private val songs = songs.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent,false)
        return SongViewHolder(view)
    }

    override fun getItemCount(): Int = songs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song.smallImageID, song.title, song.artist)
    }

    class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val ivSongImage by lazy {itemView.findViewById<ImageView>(R.id.ivSongImage)}
        private val tvSongTitle by lazy {itemView.findViewById<TextView>(R.id.tvSongTitle)}
        private val tvArtistName by lazy {itemView.findViewById<TextView>(R.id.tvArtistName)}

        fun bind(imageID: Int, songTitle: String, songArtist: String) {
            ivSongImage.setImageResource(imageID)
            tvSongTitle.text = songTitle
            tvArtistName.text = songArtist
        }
    }
}