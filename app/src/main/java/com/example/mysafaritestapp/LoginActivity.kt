package com.example.mysafaritestapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    var signUpText: TextView?= null
    var edtName: EditText ?= null
    var edtEmail: EditText ?= null
    var edtPassword: EditText ?= null
    var buttonLogin: Button?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        Finding the views by id
        signUpText = findViewById(R.id.mTvSignup)
        edtName = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtPassword = findViewById(R.id.mEdtPassword)
        buttonLogin = findViewById(R.id.mBtnLogin)

        //On Click Listener
        signUpText?.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        buttonLogin!!.setOnClickListener {
            var userName = edtName!!.toString().trim()
            var userEmail = edtEmail!!.toString().trim()
            var userPassword = edtPassword!!.toString().trim()
            var id = System.currentTimeMillis().toString()

            //Checking if a user has submitted an empty field
            if (userName.isEmpty()) {
                edtName!!.setError("PLease fill this field!!")
                edtName!!.requestFocus()
            } else if (userEmail.isEmpty()) {
                edtEmail!!.setError("Please fill this field!!")
                edtEmail!!.requestFocus()
            } else if (userPassword.isEmpty()) {
                edtPassword!!.setError("Please fill this field!!")
                edtPassword!!.requestFocus()
            } else {
                //Proceed to save data
                //Start by creating the user object
                val userData = Users(userName, userEmail, userPassword, id)
                //Creating a reference to the database to store the data
                //Does not change in any circumstance
                val reference = FirebaseDatabase.getInstance().getReference().child("Users/$id")
                //Start saving user data
                reference.setValue(userData).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext, "Data saved successfully",
                            LENGTH_LONG,
                        ).show()
                        startActivity(Intent(this,HomeActivity::class.java))
                    } else {
                        Toast.makeText(
                            applicationContext, "Data saving Failed!!",
                            LENGTH_LONG  ,
                        ).show()
                    }
                }

            }

        }
    }
}


