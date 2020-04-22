package com.davisk83.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        title = "All Songs"

        val allSongs = SongDataProvider.getAllSongs()
        val mutableSongs = allSongs.toMutableList()

        val songAdapter = SongListAdapter(allSongs)
        rvSongs.adapter = songAdapter

        songAdapter.onSongClickListener = { songTitle, songArtist ->
            tvSongID.text = getString(R.string.mini_player_text).format(songTitle, songArtist)
        }

        btnShuffle.setOnClickListener {
            val newSongs = mutableSongs.apply {
                shuffle()
            }
            songAdapter.change(newSongs)
        }
    }
}
