package com.davisk83.dotify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var randomPlayCount = Random.nextInt(1, 100000000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playCount = findViewById<TextView>(R.id.play_count)
        playCount.text = randomPlayCount.toString() + " plays"

        play_button.setOnClickListener() {
            randomPlayCount += 1
            play_count.apply {
                text = randomPlayCount.toString() + " plays"
            }
        }

        previous_button.setOnClickListener() {
            Toast.makeText(this, "Skipping to previous track", Toast.LENGTH_SHORT).show()
        }

        next_button.setOnClickListener() {
            Toast.makeText(this, "Skipping to next track", Toast.LENGTH_SHORT).show()
        }

        change_user_button.setOnClickListener() {
            username.apply {
                visibility = View.INVISIBLE
            }
            new_username.apply {
                visibility = View.VISIBLE
            }
            change_user_button.apply {
                visibility = View.INVISIBLE
            }
            apply_button.apply {
                visibility = View.VISIBLE
            }
        }

        apply_button.setOnClickListener() {
            val message = new_username.text.toString()
            if (message.trim().length > 0) {
                apply_button.apply {
                    visibility = View.INVISIBLE
                }
                change_user_button.apply {
                    visibility = View.VISIBLE
                }
                new_username.apply {
                    visibility = View.INVISIBLE
                }
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
