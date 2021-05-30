package com.anilyilmaz.socialwithkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            val intent = Intent(applicationContext,SharingActivity::class.java)
            startActivity(intent)
            finish()
        }



    }


    fun signInClicked(view : View) {

        firebaseAuth.signInWithEmailAndPassword(userEmailText.text.toString(),passwordText.text.toString()).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                Toast.makeText(applicationContext,"Welcome: ${firebaseAuth.currentUser?.email.toString()}",
                    Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,SharingActivity::class.java)
                startActivity(intent)
                finish()

            }

        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()
        }

    }

    fun signUpClicked(view : View) {

        val email = userEmailText.text.toString()
        val password = passwordText.text.toString()

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

            if (task.isSuccessful) {
                val intent = Intent(applicationContext,SharingActivity::class.java)
                startActivity(intent)
                finish()
            }

        }.addOnFailureListener { exception ->
            if (exception != null) {
                Toast.makeText(applicationContext,exception.localizedMessage.toString(), Toast.LENGTH_LONG).show()
            }
        }

    }
}

