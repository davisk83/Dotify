package com.davisk83.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.davisk83.dotify.R
import com.davisk83.dotify.fragment.OnSongClickListener
import com.davisk83.dotify.fragment.SongListFragment
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_ultimate_main.*

class UltimateMainActivity : AppCompatActivity(), OnSongClickListener {

    private val allSongs = SongDataProvider.getAllSongs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main)

        val listSongFragment = SongListFragment(allSongs)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.flFragContainer, listSongFragment)
            .commit()
    }

    override fun onSongClicked(song: Song) {
        tvSongID.text = getString(R.string.mini_player_text).format(song.title, song.artist)
    }
}
