package com.davisk83.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.davisk83.dotify.R
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.fragment_now_playing.*
import kotlin.random.Random

class NowPlayingFragment : Fragment() {

    private lateinit var song: Song
    private var randomPlayCount = Random.nextInt(1, 100000000)

    companion object {
        val TAG: String = NowPlayingFragment::class.java.simpleName
        const val ARG_SONG = "arg_song"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { args ->
            val song = args.getParcelable<Song>(ARG_SONG)
            if (song != null) {
                this.song = song
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvPlayCount.text = getString(R.string.initial_play_count).format(randomPlayCount)

        initSongID()
        initPrevClick()
        initPlayClick()
        initNextClick()
    }

    private fun initSongID() {
        tvSongTitle.text = song.title
        tvArtistName.text = song.artist
        ivCoverArt.setImageResource(song.largeImageID)
    }

    private fun initPrevClick() {
        ibPreviousButton.setOnClickListener() {
            Toast.makeText(context, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initPlayClick() {
        ibPlayButton.setOnClickListener() {
            randomPlayCount += 1
            tvPlayCount.text = getString(R.string.initial_play_count).format(randomPlayCount)
        }
    }

    private fun initNextClick() {
        ibNextButton.setOnClickListener() {
            Toast.makeText(context, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }
}
