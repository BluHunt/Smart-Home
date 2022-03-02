package com.example.sh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.welcome.*

class Welcome : AppCompatActivity() {
    private lateinit var myanim : Animation
    private lateinit var timer : Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome)
        myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition)
        //Start Animation
        ra.startAnimation(myanim)
        //thread is used for delay
        timer = Thread {
            run {
                Thread.sleep(3000)
            }
            val intent = Intent(this@Welcome, Login::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
        timer.start()
    }
}
