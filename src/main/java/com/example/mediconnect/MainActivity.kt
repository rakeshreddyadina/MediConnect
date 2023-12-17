package com.example.mediconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mediconnect.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var binding: ActivityMainBinding
private lateinit var adapter: CustomAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        binding.textView2.text = intent.getStringExtra("name")
        binding.textView1.text = intent.getStringExtra("gender")
        binding.textView3.text = intent.getStringExtra("age")
        binding.textView4.text = intent.getStringExtra("user")
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutManager
        val records1: MutableList<records> = mutableListOf()

// Get a reference to your Firebase database
        val databaseReference = FirebaseDatabase.getInstance().getReference("users/medicalRecords")

// Add a ValueEventListener to fetch the data
        var user= binding.textView4.text.toString()
        databaseReference.child(user).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Iterate through the dataSnapshot to retrieve medical records
                    for (recordSnapshot in dataSnapshot.children) {
                        val currentDate=recordSnapshot.child("currentDate").getValue(String::class.java).toString()
                        val doctorName= recordSnapshot.child("doctorName").getValue(String::class.java).toString()
                        val disease=recordSnapshot.child("disease").getValue(String::class.java).toString()
                        val medicine= recordSnapshot.child("medicine").getValue(String::class.java).toString()
                        val record=records(currentDate,disease, doctorName, medicine)
                        if (record != null) {
                            records1.add(record)
                        }
                    }
                    val adapter = CustomAdapter(records1)
                    binding.recyclerview.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "No Details Found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity, "Some Thing Went Wrong", Toast.LENGTH_SHORT).show()            }
        })

    }
}