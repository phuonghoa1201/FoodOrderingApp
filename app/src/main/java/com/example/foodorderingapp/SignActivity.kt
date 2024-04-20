package com.example.foodorderingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodorderingapp.databinding.ActivitySignBinding
import com.example.foodorderingapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var username: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private val binding: ActivitySignBinding by lazy {
        ActivitySignBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Khởi tạo Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Khởi tạo Firebase Database
        database = Firebase.database.reference

        // Sau nut dang nhap dang ky bo tam den frame ChooseLocation
        binding.signupbutton.setOnClickListener {
            username = binding.name.text.toString()
            email = binding.email.text.toString().trim()
            password = binding.password.text.toString().trim()

            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(email, password)
            }
        }
        binding.alreadyhavebutton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Account creation failed ", Toast.LENGTH_SHORT).show()
                Log.e("SignActivity", "Account creation failed", task.exception)
            }
        }
    }

    private fun saveUserData() {
        username = binding.name.text.toString().trim()
        email = binding.email.text.toString().trim()
        password = binding.password.text.toString().trim()
        val user = UserModel(username, email, password)
        val userId: String = FirebaseAuth.getInstance().currentUser!!.uid

        // Thêm log để xác minh rằng hàm được gọi và dữ liệu được ghi vào Realtime Database
        Log.d("SignActivity", "Saving user data...")

        val userRef = database.child("user").child(userId) // Tạo một tham chiếu đến nút user có ID là userId
        userRef.setValue(user) // Ghi dữ liệu của user vào nút user tương ứng

        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Xử lý sự kiện khi dữ liệu đã được ghi thành công
                Log.d("SignActivity", "User data saved successfully")
            }

            override fun onCancelled(error: DatabaseError) {
                // Xử lý sự kiện khi ghi dữ liệu thất bại
                Log.e("SignActivity", "Failed to save user data", error.toException())
            }
        })
    }
}
