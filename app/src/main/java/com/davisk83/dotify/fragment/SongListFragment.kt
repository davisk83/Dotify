package com.davisk83.dotify.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davisk83.dotify.R
import com.davisk83.dotify.SongListAdapter
import com.davisk83.dotify.activity.UltimateMainActivity
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment(allSongs: List<Song>) : Fragment() {

    private val songAdapter = SongListAdapter(allSongs)
    private lateinit var onSongClickListener: OnSongClickListener

    companion object {
        val TAG: String = SongListFragment::class.java.simpleName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvSongs.adapter = songAdapter

        songAdapter.onSongClickListener = { song ->
            onSongClickListener.onSongClicked(song)
        }
    }

    fun shuffleList() {
        val newSongs = UltimateMainActivity.allSongs.toMutableList().apply {
            shuffle()
        }
        songAdapter.change(newSongs)
        rvSongs.scrollToPosition(0)
    }
}

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}