package com.davisk83.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

// Push Test
class MainActivity : AppCompatActivity() {
    private var randomPlayCount = Random.nextInt(1, 100000000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play_count.text = getString(R.string.initialPlayCount).format(randomPlayCount)

        initPrevClick()
        initPlayClick()
        initNextClick()
        initChangeClick()
        initApplyClick()
    }

    private fun initPrevClick() {
        previous_button.setOnClickListener() {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initPlayClick() {
        play_button.setOnClickListener() {
            randomPlayCount += 1
            play_count.text = getString(R.string.initialPlayCount).format(randomPlayCount)
        }
    }

    private fun initNextClick() {
        next_button.setOnClickListener() {
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initChangeClick() {
        change_user_button.setOnClickListener() {
            username.visibility = View.INVISIBLE
            new_username.visibility = View.VISIBLE
            change_user_button.visibility = View.INVISIBLE
            apply_button.visibility = View.VISIBLE
        }
    }

    private fun initApplyClick() {
        apply_button.setOnClickListener() {
            val message = new_username.text.toString()
            if (message.trim().length > 0) {
                apply_button.visibility = View.INVISIBLE
                change_user_button.visibility = View.VISIBLE
                new_username.visibility = View.INVISIBLE
                username.apply {
                    visibility = View.VISIBLE
                    text = new_username.text
                }
            } else {
                Toast.makeText(this, "Error! Please enter username", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
