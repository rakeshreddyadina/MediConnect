package com.example.mediconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mediconnect.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener

private lateinit var loginBinding: ActivityLoginBinding
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        FirebaseApp.initializeApp(this)
        loginBinding.textView.setOnClickListener(View.OnClickListener {
            val intent=Intent(this,LoginActivity2::class.java)
            startActivity(intent)
            finish()
        })
        loginBinding.login.setOnClickListener(View.OnClickListener {
            if(loginBinding.user.text.toString().isNotBlank()&& loginBinding.password.text.toString().isNotBlank()){
                val databaseReference = FirebaseDatabase.getInstance().getReference("users/patients")
                val userId = loginBinding.user.text.toString()
                databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()) {
                            val patientName =
                                dataSnapshot.child("name").getValue(String::class.java)
                            val patientAge = dataSnapshot.child("age").getValue(String::class.java)
                            val patientGender =
                                dataSnapshot.child("gender").getValue(String::class.java)
                            val patientPassword =
                                dataSnapshot.child("password").getValue(String::class.java)
                            val password= loginBinding.password.text.toString()
                            if (patientPassword == password) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Password is correct",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent=Intent(this@LoginActivity,MainActivity::class.java)
                                    .putExtra("name",patientName)
                                    .putExtra("age",patientAge)
                                    .putExtra("gender",patientGender)
                                    .putExtra("user",userId)
                                startActivity(intent)
                                finish()
                            } else
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Incorrect Password",
                                    Toast.LENGTH_SHORT
                                ).show()

                        }
                        else {
                            Toast.makeText(this@LoginActivity, "Patient Not Found", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(this@LoginActivity, "Some Thing Went Wrong", Toast.LENGTH_SHORT).show()
                    }
                }) }
            else
                Toast.makeText(this, "Enter Details", Toast.LENGTH_SHORT).show()
                })
        loginBinding.text6.setOnClickListener(View.OnClickListener {
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}