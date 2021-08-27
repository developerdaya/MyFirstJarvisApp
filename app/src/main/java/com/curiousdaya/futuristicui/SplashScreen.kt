package com.curiousdaya.futuristicui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognizerIntent
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.*
import kotlin.collections.ArrayList

// Lets Understand Logic behind It
class SplashScreen : AppCompatActivity() {
    lateinit var helloData1:String
    lateinit var helloData2:String
    lateinit var helloData3:String
    // use two lateinit variable for comparing two string
    lateinit var mp1:MediaPlayer
    lateinit var mp2:MediaPlayer
    lateinit var mp3:MediaPlayer
    lateinit var mp4:MediaPlayer
    lateinit var mp5:MediaPlayer

    // use five late init variable of Media Player

    private val REQUEST_CODE_SPEECH_INPUT = 100
    // define REQUEST_CODE_SPEECH_INPUT constant variable to 100



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
           SpeechFunction() // I use 6 sec delay for calling SpeechFunctions
            // BECAUSE WE WILL PLAY 5 SEC WELCOME AUDIO WHILE onCreate
        }, 6500)

        splashLogo.setOnClickListener {
            SpeechFunction()

        }

         val animation : Animation = AnimationUtils.loadAnimation(this,R.anim.splash_animation)
            splashLogo.startAnimation(animation)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

         mp1 = MediaPlayer.create(this, R.raw.scifiaudio1)
         mp1.start()
         mp1.isLooping = true
         mp2= MediaPlayer.create(this, R.raw.welcome_audio)
         mp2.start()
        // while onCreate Function I am Playing two audio
        //1st whole background sound
        // 2nd welcome sound

    }

    private fun SpeechFunction() {
        // this is the code of Inbuilt Speech Recognition function
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Hello Google..")
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        }
        catch (exp: ActivityNotFoundException) {
            Toast.makeText(this, "Speech Not Supported..", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // this is the code of onActivity Result
        //mtlb ye code tab chalega jab Speech Recognition function successfully Voice ko record kar chuka hoga
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_SPEECH_INPUT)
        {
            if (requestCode == RESULT_OK||null!=data)
            {
                val res:ArrayList<String> = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                helloData1 = res[0]
                mp5= MediaPlayer.create(this, R.raw.please_wait)
                mp5.start()
                    // after voice record please wait audio will be play
            }
              helloData2 = "hello Google" // for online
              helloData3 = "hello google" // for offline
            // because google devta jo hai online Capital "Google" Likhte hai aur Offline Small "google"

            Handler(Looper.getMainLooper()).postDelayed({
                if (helloData1==helloData2 ||  helloData1==helloData3)
                // here is the main logic where we are comparing hello Google String
                // to google  Speech Recognition String if hello google = hello google
                // then open a new activity
                {

                    mp3= MediaPlayer.create(this, R.raw.thankyou)
                    mp3.start()
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(Intent(this,MainActivity::class.java))
                    }, 3000)
                }
                else
                {
                    mp4= MediaPlayer.create(this, R.raw.sorry)
                    mp4.start()
                    Handler(Looper.getMainLooper()).postDelayed({
                        SpeechFunction()
                    }, 5000)

                }

            }, 5000)
                // rest of code you can see on you screen
        }
    }

    override fun onStop() {
        super.onStop()
        mp1.stop()

    }



}