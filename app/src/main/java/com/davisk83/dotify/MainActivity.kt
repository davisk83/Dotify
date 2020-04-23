package com.davisk83.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.ericchee.songdataprovider.Song
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object{
        const val SONG_KEY = "SONG_KEY"
    }

    private var randomPlayCount = Random.nextInt(1, 100000000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvPlayCount.text = getString(R.string.initial_play_count).format(randomPlayCount)

        initSongID()
        initPrevClick()
        initPlayClick()
        initNextClick()
        initChangeClick()
        initApplyClick()
    }

    private fun initSongID() {
        val song = intent.getParcelableExtra<Song>(SONG_KEY)
        tvSongTitle.text = song.title
        tvArtistName.text = song.artist
        ivCoverArt.setImageResource(song.largeImageID)
    }

    private fun initPrevClick() {
        ibPreviousButton.setOnClickListener() {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initChangeClick() {
        btnChangeUser.setOnClickListener() {
            tvUsername.visibility = View.INVISIBLE
            etNewUsername.visibility = View.VISIBLE
            btnChangeUser.visibility = View.INVISIBLE
            btnApply.visibility = View.VISIBLE
        }
    }

    private fun initApplyClick() {
        btnApply.setOnClickListener() {
            val message = etNewUsername.text.toString()
            if (message.trim().length > 0) {
                btnApply.visibility = View.INVISIBLE
                btnChangeUser.visibility = View.VISIBLE
                etNewUsername.visibility = View.INVISIBLE
                tvUsername.apply {
                    visibility = View.VISIBLE
                    text = etNewUsername.text
                }
            } else {
                Toast.makeText(this, "Error! Please enter username", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
