package com.example.mediconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mediconnect.databinding.ActivityLogin2Binding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener

private lateinit var loginBinding2: ActivityLogin2Binding

class LoginActivity2() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding2 = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(loginBinding2.root)
        FirebaseApp.initializeApp(this)
        val databaseReference = FirebaseDatabase.getInstance().getReference("users/nominees")
        val databaseReference1 = FirebaseDatabase.getInstance().getReference("users/patients")

        loginBinding2.textView.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
        loginBinding2.login.setOnClickListener(View.OnClickListener {
            val nomineeName= loginBinding2.nominee.text.toString()
            val user= loginBinding2.user.text.toString()
            val nomineePass= loginBinding2.password.text.toString()
            var nomineeName1:String
            var nomineePass1:String
            var user1:String
            var patientName:String
            var patientAge:String
            var patientGender:String
            databaseReference.child(nomineeName).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        nomineeName1= dataSnapshot.child("nomineeId").getValue(String::class.java).toString()
                        nomineePass1=
                            dataSnapshot.child("nomineePass").getValue(String::class.java).toString()
                        user1= dataSnapshot.child("userId").getValue(String::class.java).toString()
                        databaseReference1.child(user).addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    patientName=dataSnapshot.child("name").getValue(String::class.java).toString()
                                    patientAge=dataSnapshot.child("age").getValue(String::class.java).toString()
                                    patientGender=dataSnapshot.child("gender").getValue(String::class.java).toString()
                                    if(user==user1&&nomineePass==nomineePass1){
                                    var intent=Intent(this@LoginActivity2,MainActivity::class.java).putExtra("name",patientName).putExtra("age",patientAge).putExtra("gender",patientGender).putExtra("user",user1)
                                    startActivity(intent)
                                    finish()
                                    }
                                }
                                else{
                                    Toast.makeText(this@LoginActivity2, "Data Doesn't Exist", Toast.LENGTH_SHORT).show()
                                }
                            }
                            override fun onCancelled(databaseError: DatabaseError) {
                                Toast.makeText(this@LoginActivity2,"Something Went Wrong",Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    else{
                        Toast.makeText(this@LoginActivity2, "Data Doesn't Exist", Toast.LENGTH_SHORT).show()
                    }
            }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@LoginActivity2,"Something Went Wrong",Toast.LENGTH_SHORT).show()
                }
        })
    })
}
}

