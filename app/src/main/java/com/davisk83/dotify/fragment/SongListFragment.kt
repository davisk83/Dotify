package com.davisk83.dotify.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davisk83.dotify.R
import com.davisk83.dotify.SongListAdapter
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_song_list.*

class SongListFragment() : Fragment() {

    private lateinit var allSongs: List<Song>
    private lateinit var newSongs: ArrayList<Song> // for saved instance state
    private lateinit var songAdapter: SongListAdapter
    private lateinit var onSongClickListener: OnSongClickListener

    companion object {
        const val ARG_SONG_LIST = "arg_song_list"
        val TAG: String = SongListFragment::class.java.simpleName
        private const val OUT_SONG_LIST = "out_song_list"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnSongClickListener) {
            onSongClickListener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListOfSongs()
        checkSaveInstanceState(savedInstanceState)
    }

    private fun initListOfSongs() {
        arguments?.let { args ->
            val allSongs = args.getParcelableArrayList<Song>(ARG_SONG_LIST)
            if (allSongs != null) {
                this.allSongs = allSongs.toList()
                newSongs = allSongs // safe check to avoid null pointer exception
            }
        }
    }

    private fun checkSaveInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                newSongs = getParcelableArrayList<Song>(OUT_SONG_LIST) as ArrayList<Song>
                songAdapter = SongListAdapter(newSongs)
            }
        } else {
            songAdapter = SongListAdapter(allSongs)
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
        val newSongs = allSongs.toMutableList().apply {
            shuffle()
        }
        this.newSongs = newSongs as ArrayList<Song> // for saved instance state
        songAdapter.change(newSongs)
        rvSongs.scrollToPosition(0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(OUT_SONG_LIST, newSongs)

        super.onSaveInstanceState(outState)
    }
}

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}