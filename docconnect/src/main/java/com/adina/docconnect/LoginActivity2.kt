package com.adina.docconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.adina.docconnect.databinding.ActivityLogin2Binding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding: ActivityLogin2Binding
class LoginActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signup.setOnClickListener(View.OnClickListener {
            var name= binding.name.text.toString()
            var userId= binding.user.text.toString()
            var pass= binding.password.text.toString()
            val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
            if(name.isNotBlank()&&userId.isNotBlank()&&pass.isNotBlank()){
                val doctor=Doctor(name, userId, pass)
                databaseReference.child("users").child("doctors").child(userId).setValue(doctor)
                var intent= Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this@LoginActivity2, "Enter Details", Toast.LENGTH_SHORT).show()
            }

        })
        binding.signin.setOnClickListener(View.OnClickListener {
            var intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}