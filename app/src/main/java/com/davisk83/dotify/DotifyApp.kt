package com.davisk83.dotify

import android.app.Application

class DotifyApp: Application() {

    var ad = true
    var adTimer = 5

    fun onSongClicked () {
        ad = true
        if (adTimer == 0) {
            adTimer = 5
        }
        adTimer--
    }
}