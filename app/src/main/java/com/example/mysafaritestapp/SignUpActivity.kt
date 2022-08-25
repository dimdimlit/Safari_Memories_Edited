package com.example.mysafaritestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {



    var editTextName: EditText?= null
    var editTextEmail: EditText?= null
    var editTextPassword: EditText?= null
    var editTextConfPassword: EditText?= null
    var buttonSignUp: Button?= null
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //Finding the views by id
        editTextName = findViewById(R.id.mEdtSignUpName)
        editTextEmail = findViewById(R.id.mEdtSignUpEmail)
        editTextPassword = findViewById(R.id.mEdtSignUpPass)
        editTextConfPassword = findViewById(R.id.mEdtSignUpConfPass)
        buttonSignUp = findViewById(R.id.mBtnSignup)


                //Setting the listeners
                buttonSignUp!!.setOnClickListener {
                    var userName = editTextName!!.text.toString().trim()
                    var userEmail = editTextEmail!!.text.toString().trim()
                    var userPassword = editTextPassword!!.text.toString().trim()
                    var userConfPassword = editTextConfPassword!!.text.toString().trim()
                    var id = System.currentTimeMillis().toString()

                    //Checking if a user has submitted an empty field
                    if (userName.isEmpty()) {
                        editTextName!!.setError("PLease fill this field!!")
                        editTextName!!.requestFocus()
                    } else if (userEmail.isEmpty()) {
                        editTextEmail!!.setError("Please fill this field!!")
                        editTextEmail!!.requestFocus()
                    } else if (userPassword.isEmpty()) {
                        editTextEmail!!.setError("Please fill this field!!")
                        editTextEmail!!.requestFocus()
                    } else if (userConfPassword != userPassword) {
                        editTextConfPassword!!.setError("Password Does Not Match!!!")
                        editTextConfPassword!!.requestFocus()
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
                                    Toast.LENGTH_LONG
                                ).show()
                                startActivity(Intent(this,HomeActivity::class.java))
                            } else {
                                Toast.makeText(
                                    applicationContext, "Data saving Failed!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }
                }




        }



}

