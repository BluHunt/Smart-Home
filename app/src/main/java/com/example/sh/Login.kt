package com.example.sh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.example.sh.login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.login.*

class Login : AppCompatActivity() {
    private lateinit var db: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        db = FirebaseDatabase.getInstance().reference.child("Users")
        mAuth = FirebaseAuth.getInstance()
        progressBar.visibility = View.INVISIBLE
        login.setOnClickListener {
            login.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
            val email = user_email.text.toString().trim()
            val password = user_pass.text.toString().trim()
            if (email.isEmpty()) {
                user_email.error = "Email Required"
                user_email.requestFocus()
                login.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                user_email.error = "Valid Email Required"
                user_email.requestFocus()
                login.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                return@setOnClickListener
            }
            if (password.isEmpty() || password.length < 8) {
                user_pass.error = "8 char Password Required"
                user_pass.requestFocus()
                login.visibility = View.VISIBLE
                progressBar.visibility = View.INVISIBLE
                return@setOnClickListener
            }
            // mdatabase.child(id).setValue(users)
            uservalidation(email, password)
        }
    }
    private fun uservalidation(email: String, password: String) {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    var uls : Boolean = false
                    for(u in p0.children){
                        if(u.child("email").value.toString() == email && u.child("password").value.toString() == password){
                            uls = true
                            user_login(email,password)
                        }
                    }
                    if(uls == false){
                        toast("User Not Found In Database!!!")
                        login.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    private fun user_login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task1 ->
                if(task1.isSuccessful){
                    toast("Login Successfully")
                    login()
                    finish()
                }else{
                    toast("Failed!!")
                }
            }
    }

    override fun onStart() {
        super.onStart()
        mAuth.currentUser?.let {
            login()
            welcome()
            finish()
        }
    }
}
