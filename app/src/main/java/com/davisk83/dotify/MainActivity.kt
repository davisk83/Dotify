package com.davisk83.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var randomPlayCount = Random.nextInt(1, 100000000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvPlayCount.text = getString(R.string.initial_play_count).format(randomPlayCount)

        initPrevClick()
        initPlayClick()
        initNextClick()
        initChangeClick()
        initApplyClick()
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
}
