package com.adina.docconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adina.docconnect.databinding.ActivityMain2Binding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val userId = intent.getStringExtra("userId").toString()

        binding.submit.setOnClickListener(View.OnClickListener {
            val disease = binding.disease1.text.toString()
            val medicine = binding.medicine1.text.toString()
            val doctorName = intent.getStringExtra("name").toString()
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1 // Month is zero-based, so add 1
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val currentDate = "$year-$month-$dayOfMonth"
            val userId = intent.getStringExtra("userId").toString()
            val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
            if (disease.isNotBlank() && medicine.isNotBlank()) {
                val medicalRecords = records(disease, medicine, doctorName, currentDate)
                databaseReference.child("users").child("medicalRecords").child(userId).child(currentDate)
                    .setValue(medicalRecords)
                Toast.makeText(this, "Data Added Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity2, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
                Toast.makeText(this, "Enter Details", Toast.LENGTH_SHORT).show()
        })
            binding.back.setOnClickListener(View.OnClickListener {
                val intent = Intent(this@MainActivity2, MainActivity::class.java)
                startActivity(intent)
                finish()
            })
    }
}