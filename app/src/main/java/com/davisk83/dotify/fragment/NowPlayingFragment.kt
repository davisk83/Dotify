package com.davisk83.dotify.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.davisk83.dotify.DotifyApp
import com.davisk83.dotify.R
import com.davisk83.dotify.model.UserInfo
import com.ericchee.songdataprovider.Song
import com.google.gson.Gson
import com.squareup.picasso.Picasso
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

        fetchUserInfo()
        initSongClicked()
        checkSaveInstanceState(savedInstanceState)
    }

    private fun fetchUserInfo() {
        val queue = Volley.newRequestQueue(this.context)
        val profileURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/user_info.json"

        val request = StringRequest(Request.Method.GET, profileURL,
            { response ->
                val gson = Gson()
                val userInfo = gson.fromJson(response, UserInfo::class.java)
                tvUsername.text = userInfo.username
                Picasso.get().load(userInfo.profilePicURL).into(ivProfilePic)
            },
            { error ->
                ivProfilePic.visibility = View.INVISIBLE
                tvUsername.text = getString(R.string.error_handling)
            }
        )

        queue.add(request)
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

        initAd()
        initSongID()
        initPrevClick()
        initPlayClick()
        initNextClick()
    }

    private fun initAd() {
        if ((context?.applicationContext as DotifyApp).ad) {
            val adTimer = (context?.applicationContext as DotifyApp).adTimer
            if (adTimer == 0) {
                Toast.makeText(context, "Follow my GitHub account, @https://github.com/davisk83 :)", Toast.LENGTH_LONG).show()
            } else if (adTimer > 1) {
                Toast.makeText(context, "$adTimer more songs before an ad!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "$adTimer more song before an ad!", Toast.LENGTH_SHORT).show()
            }
            (context?.applicationContext as DotifyApp).ad = false
        }
    }

    private fun initSongID() {
        ivCoverArt.setImageResource(song.largeImageID)
        tvSongTitle.text = song.title
        tvArtistName.text = song.artist
        tvPlayCount.text = getString(R.string.initial_play_count).format(randomPlayCount) // sets initial song play count
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
