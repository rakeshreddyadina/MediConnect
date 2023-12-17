package com.adina.docconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.adina.docconnect.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


private lateinit var loginBinding: ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        FirebaseApp.initializeApp(this)
        loginBinding.register.setOnClickListener(View.OnClickListener {
            val intent= Intent(this,LoginActivity2::class.java)
            startActivity(intent)
            finish()
        })
        loginBinding.details.setOnClickListener(View.OnClickListener {
            val userId= loginBinding.user.text.toString()
            val password= loginBinding.password.text.toString()
            if(userId.isNotBlank()&&password.isNotBlank()){
                val databaseReference = FirebaseDatabase.getInstance().getReference("users/doctors")
                databaseReference.child(userId).addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val doctorName =
                                dataSnapshot.child("name").getValue(String::class.java).toString()
                            val doctorPassword=dataSnapshot.child("pass").getValue(String::class.java).toString()
                            if (doctorPassword == password) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Password is correct",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val doctorName = dataSnapshot.child("name").getValue(String::class.java).toString()
                                  val intent= Intent(this@LoginActivity,MainActivity::class.java).putExtra("name",doctorName)
                                  startActivity(intent)
                                  finish()
                            } else
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Password is Incorrect",
                                    Toast.LENGTH_SHORT
                                ).show()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@LoginActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                    }
                })

            }
            else
                Toast.makeText(this@LoginActivity, "Enter Details", Toast.LENGTH_SHORT).show()
        })
    }
}