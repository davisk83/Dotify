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

    private lateinit var song: Song // for saved instance state
    private var randomPlayCount = Random.nextInt(1, 100000000)

    companion object {
        const val ARG_SONG = "arg_song"
        val TAG: String = NowPlayingFragment::class.java.simpleName
        private const val OUT_COUNT = "out_count"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initSongClicked()
        checkSaveInstanceState(savedInstanceState)
    }

    private fun initSongClicked() {
        arguments?.let { args ->
            val song = args.getParcelable<Song>(ARG_SONG)
            if (song != null) {
                this.song = song
            }
        }
    }

    private fun checkSaveInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            with(savedInstanceState) {
                randomPlayCount = getInt(OUT_COUNT, -1)
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

        tvPlayCount.text = getString(R.string.initial_play_count).format(randomPlayCount) // sets initial song play count

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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(OUT_COUNT, randomPlayCount)

        super.onSaveInstanceState(outState)
    }
}
