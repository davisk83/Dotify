package com.davisk83.dotify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song

class SongListAdapter(songs: List<Song>): RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {

    private var songs = songs.toList()

    var onSongClickListener: ((songTitle: String, songArtist: String) -> Unit)? = null
    var onMiniPlayerClickListener: ((song: Song) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent,false)
        return SongViewHolder(view)
    }

    override fun getItemCount(): Int = songs.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.bind(song, song.smallImageID, song.title, song.artist)
    }

    fun change(newSongs: List<Song>) {
        val callback = SongDiffCallback(songs, newSongs)
        val diffResult = DiffUtil.calculateDiff(callback)
        diffResult.dispatchUpdatesTo(this)

        songs = newSongs
    }

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val ivSongImage by lazy {itemView.findViewById<ImageView>(R.id.ivSongImage)}
        private val tvSongTitle by lazy {itemView.findViewById<TextView>(R.id.tvSongTitle)}
        private val tvArtistName by lazy {itemView.findViewById<TextView>(R.id.tvArtistName)}

        fun bind(
            song: Song,
            smallImageID: Int,
            songTitle: String,
            songArtist: String
        ) {
            ivSongImage.setImageResource(smallImageID)
            tvSongTitle.text = songTitle
            tvArtistName.text = songArtist

            itemView.setOnClickListener {
                onSongClickListener?.invoke(songTitle, songArtist)
                onMiniPlayerClickListener?.invoke(song)
            }
        }
    }
}