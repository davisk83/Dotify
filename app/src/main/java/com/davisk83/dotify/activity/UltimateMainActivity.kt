package com.davisk83.dotify.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.davisk83.dotify.R
import com.davisk83.dotify.fragment.NowPlayingFragment
import com.davisk83.dotify.fragment.OnSongClickListener
import com.davisk83.dotify.fragment.SongListFragment
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider
import kotlinx.android.synthetic.main.activity_ultimate_main.*
import kotlinx.android.synthetic.main.fragment_song_list.*

class UltimateMainActivity : AppCompatActivity(), OnSongClickListener {

    companion object {
        val allSongs = SongDataProvider.getAllSongs()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate_main)

        initMiniPlayer()
        initRecyclerView()
        initShuffleBtn()

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0

            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        clMiniPlayer.visibility = View.VISIBLE
        rvSongs.visibility = View.VISIBLE
        return super.onNavigateUp()
    }

    private fun initMiniPlayer() {
        clMiniPlayer.setOnClickListener {
            Toast.makeText(this, "Select a song", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecyclerView() {
        val listSongFragment = SongListFragment(allSongs)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flFragContainer, listSongFragment, SongListFragment.TAG)
            .commit()
    }

    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    private fun initShuffleBtn() {
        btnShuffle.setOnClickListener {
            getSongListFragment()?.shuffleList()
        }
    }

    override fun onSongClicked(song: Song) {
        tvSongID.text = getString(R.string.mini_player_text).format(song.title, song.artist)
        clMiniPlayer.setOnClickListener {
            showNowPlayingFragment(song)
        }
    }

    private fun showNowPlayingFragment(song: Song) {
        clMiniPlayer.visibility = View.GONE
        rvSongs.visibility = View.GONE
        val songDetailFragment = NowPlayingFragment()
        val argumentBundle = Bundle().apply {
            putParcelable(NowPlayingFragment.ARG_SONG, song)
        }
        songDetailFragment.arguments = argumentBundle
        supportFragmentManager
            .beginTransaction()
            .add(R.id.flFragContainer, songDetailFragment)
            .addToBackStack(NowPlayingFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        clMiniPlayer.visibility = View.VISIBLE
        rvSongs.visibility = View.VISIBLE
        super.onBackPressed()
    }
}
