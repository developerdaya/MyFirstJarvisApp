package com.curiousdaya.futuristicui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    lateinit var mp1: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)


        mp1 = MediaPlayer.create(this, R.raw.fullaudio)
        mp1.start()
        mp1.isLooping = true

    }

    override fun onStop() {
        super.onStop()
        mp1.stop()
    }

    override fun onPause() {
        super.onPause()
        mp1.stop()

    }
}