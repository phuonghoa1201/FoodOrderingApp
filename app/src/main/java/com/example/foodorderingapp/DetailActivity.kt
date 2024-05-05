package com.example.foodorderingapp

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.foodorderingapp.databinding.ActivityDetailBinding
import com.example.foodorderingapp.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var foodName: String?=null
    private var foodImage: String?=null
    private var foodDescription: String?=null
    private var foodIngredients: String?=null
    private var foodPrice: String?=null
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initialize Firebase
        auth = FirebaseAuth.getInstance()

        foodName = intent.getStringExtra("menuItemName")
        foodDescription = intent.getStringExtra("menuItemDescription")
        foodIngredients = intent.getStringExtra("menuItemIngredients")
        foodPrice = intent.getStringExtra("menuItemPrice")
        foodImage = intent.getStringExtra("menuItemImage")
        with(binding){
            tvFoodName.text =foodName
            tvDetailFoodName.text=foodDescription
            tvIngredient.text=foodIngredients
            Glide.with(this@DetailActivity).load(Uri.parse(foodImage)).into(imgDetail)
        }



        binding.imgBack.setOnClickListener {
            finish()
        }
        binding.addItemBtn.setOnClickListener {
            addItemToCart()
        }






    }

    private fun addItemToCart() {
        val database = FirebaseDatabase.getInstance().reference
        val userId = auth.currentUser?.uid?:""
//        create a cartItem object
        val cartItem = CartItem(foodName.toString(),foodPrice.toString(),foodDescription.toString(),foodImage.toString(), 1,foodIngredients.toString())
//       save data to cart item to firebase
        database.child("user").child(userId).child("CartItems").push().setValue(cartItem).addOnSuccessListener {
            Toast.makeText(this,"Item added to card",Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(this,"Item hasn't added to card",Toast.LENGTH_SHORT).show()

        }
    }
}