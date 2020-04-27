package com.davisk83.dotify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davisk83.dotify.MainActivity.Companion.SONG_KEY
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_song_list.*

class SongListActivity : AppCompatActivity() {

    private val allSongs = SongDataProvider.getAllSongs()
    private val songAdapter = SongListAdapter(allSongs)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        title = "All Songs"

        initRecyclerView()
        initShuffleClick()
    }

    private fun initRecyclerView() {
        rvSongs.adapter = songAdapter
        val songPlayer = Intent(this, MainActivity::class.java)

        songAdapter.onSongClickListener = { song ->
            tvSongID.text = getString(R.string.mini_player_text).format(song.title, song.artist)
            songPlayer.putExtra(SONG_KEY, song)
        }

        clMiniPlayer.setOnClickListener {
            startActivity(songPlayer)
        }
    }

    private fun initShuffleClick() {
        btnShuffle.setOnClickListener {
            val newSongs = allSongs.toMutableList().apply {
                shuffle()
            }
            songAdapter.change(newSongs)
            rvSongs.scrollToPosition(0)
        }
    }
}
