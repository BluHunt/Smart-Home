package com.example.sh

import android.content.Context
import android.content.Intent
import android.widget.TextView
import android.widget.Toast

var user : String = "Welcome"
fun Context.setheader(){


}

fun Context.toast(message: String){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
}

fun Context.welcome(){
    toast(user)
}

fun Context.login(){
    val intent = Intent(this, Home::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}

fun Context.logout(){
    val intent = Intent(this, Login::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}