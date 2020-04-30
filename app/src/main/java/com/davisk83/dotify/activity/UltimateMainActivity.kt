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

class UltimateMainActivity : AppCompatActivity(), OnSongClickListener {

    private val allSongs = SongDataProvider.getAllSongs()
    private var song: Song? = null

    companion object {
        private const val OUT_SONG = "out_song"
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

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                song = this.getParcelable(OUT_SONG)
                song?.let {
                    onSongClicked(it)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        clMiniPlayer.visibility = View.VISIBLE
        return super.onNavigateUp()
    }

    private fun initMiniPlayer() {
        clMiniPlayer.setOnClickListener {
            Toast.makeText(this, "Select a song", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRecyclerView() {
        var listSongFragment = getSongListFragment()
        if (listSongFragment == null) {
            val songArrayList: ArrayList<Song> = allSongs as ArrayList<Song>
            listSongFragment = SongListFragment()
            val argumentBundle = Bundle().apply {
                putParcelableArrayList(SongListFragment.ARG_SONG_LIST, songArrayList)
            }
            listSongFragment.arguments = argumentBundle
            supportFragmentManager
                .beginTransaction()
                .add(R.id.flFragContainer, listSongFragment, SongListFragment.TAG)
                .commit()
         }
    }

    private fun initShuffleBtn() {
        btnShuffle.setOnClickListener {
            getSongListFragment()?.shuffleList()
        }
    }

    override fun onSongClicked(song: Song) {
        this.song = song
        tvSongID.text = getString(R.string.mini_player_text).format(song.title, song.artist)
        clMiniPlayer.setOnClickListener {
            showNowPlayingFragment(song)
        }
    }

    private fun showNowPlayingFragment(song: Song) {
        clMiniPlayer.visibility = View.GONE
        val songDetailFragment = NowPlayingFragment()
        val argumentBundle = Bundle().apply {
            putParcelable(NowPlayingFragment.ARG_SONG, song)
        }
        songDetailFragment.arguments = argumentBundle
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flFragContainer, songDetailFragment)
            .addToBackStack(NowPlayingFragment.TAG)
            .commit()
    }

    override fun onBackPressed() {
        clMiniPlayer.visibility = View.VISIBLE
        super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(OUT_SONG, song)
        super.onSaveInstanceState(outState)
    }

    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment
}
