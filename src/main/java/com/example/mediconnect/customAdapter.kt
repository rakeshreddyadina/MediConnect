package com.example.mediconnect

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mediconnect.databinding.ActivityHealthRecordsBinding

class CustomAdapter(private val records: List<records>) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ActivityHealthRecordsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val doctorName: TextView = binding.doctor
        val medicine: TextView = binding.medicine
        val disease: TextView = binding.disease
        val currentDate: TextView = binding.date
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =ActivityHealthRecordsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = records[position]
        holder.doctorName.text=record.doctorName
        holder.medicine.text=record.medicine
        holder.currentDate.text=record.currentDate
        holder.disease.text=record.disease
    }

    override fun getItemCount(): Int {
        return records.size
    }
}