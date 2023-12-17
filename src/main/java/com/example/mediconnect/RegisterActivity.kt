package com.example.mediconnect
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mediconnect.databinding.ActivityRegisterBinding
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

private lateinit var registerActivityBinding:ActivityRegisterBinding
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerActivityBinding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerActivityBinding.root)
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
        registerActivityBinding.signin.setOnClickListener(View.OnClickListener {
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
        registerActivityBinding.signup.setOnClickListener(View.OnClickListener {
            if(registerActivityBinding.name.text.toString().isNotBlank() && (registerActivityBinding.age.text.toString().isNotBlank())&& (registerActivityBinding.user.text.toString().isNotBlank())&& (registerActivityBinding.password.text.toString().isNotBlank())&& (registerActivityBinding.nominee.text.toString().isNotBlank())&&(registerActivityBinding.nomineePass.text.toString().isNotBlank())&&(registerActivityBinding.male.isChecked|| registerActivityBinding.female.isChecked)) {
                val gender:String
                if(registerActivityBinding.male.isChecked)
                    gender="Male"
                else
                    gender="Female"
                val patient =Patient(registerActivityBinding.name.text.toString(),
                    registerActivityBinding.age.text.toString(),gender, registerActivityBinding.user.text.toString(),
                    registerActivityBinding.nominee.text.toString(), registerActivityBinding.password.text.toString())
                val nominee=Nominee(registerActivityBinding.user.text.toString(),
                    registerActivityBinding.nominee.text.toString(), registerActivityBinding.nomineePass.text.toString())
                databaseReference.child("users").child("patients").child(registerActivityBinding.user.text.toString()).setValue(patient)
                databaseReference.child("users").child("nominees").child(registerActivityBinding.nominee.text.toString()).setValue(nominee)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this,"Enter all Details", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
