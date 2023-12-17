package com.adina.docconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adina.docconnect.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layoutManager
        val doctorName=intent.getStringExtra("name")
        binding.details.setOnClickListener(View.OnClickListener {
            val userId=binding.user.text.toString()
            val records1: MutableList<records> = mutableListOf()
            if(userId.isNotBlank()) {
                val databaseReference =
                    FirebaseDatabase.getInstance().getReference("users/medicalRecords")
                databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
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
            else
                Toast.makeText(this@MainActivity, "Enter Details", Toast.LENGTH_SHORT).show()
                    })
        binding.floatingActionButton.setOnClickListener(View.OnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().getReference("users/patients")
            val userId=binding.user.text.toString()
            if(userId.isNotBlank()){
            databaseReference.child(userId).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val patientName =
                            dataSnapshot.child("name").getValue(String::class.java)
                        Toast.makeText(this@MainActivity, "Valid Details", Toast.LENGTH_SHORT).show()
                        val intent= Intent(this@MainActivity,MainActivity2::class.java).putExtra("name",doctorName).putExtra("userId",userId)
                        startActivity(intent)
                        finish()

                    }
                    else
                        Toast.makeText(this@MainActivity, "Invalid Details", Toast.LENGTH_SHORT).show()
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MainActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            })

            }
            else
                Toast.makeText(this@MainActivity, "Enter Details", Toast.LENGTH_SHORT).show()
        })
    }
}