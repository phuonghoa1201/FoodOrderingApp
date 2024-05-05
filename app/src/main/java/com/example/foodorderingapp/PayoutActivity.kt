package com.example.foodorderingapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodorderingapp.databinding.ActivityPayoutBinding
import com.example.foodorderingapp.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayoutActivity : AppCompatActivity() {
    lateinit var binding:ActivityPayoutBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var name:String
    private lateinit var address:String
    private lateinit var phone:String
    private lateinit var totalAmount:String
    private lateinit var foodName: ArrayList<String>
    private lateinit var foodPrice:ArrayList<String>
    private lateinit var foodDescription:ArrayList<String>
    private lateinit var foodImage:ArrayList<String>
    private lateinit var foodIngredient:ArrayList<String>
    private lateinit var foodQuantities:ArrayList<Int>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        Initialize firebase and User details
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
//        set user data
        setUserData()
        binding.placeMyOrderBtn.setOnClickListener {
//            get data from textView
            name = binding.nameEdt.text.toString().trim()
            address = binding.addressEdt.text.toString().trim()
            phone = binding.phoneEdt.text.toString().trim()
            if(name.isBlank()&&address.isBlank()&&phone.isBlank()){
                Toast.makeText(this,"Please enter all the information",Toast.LENGTH_SHORT).show()
            }else{
                placeOrder()
            }
//            val bottomSheetDialog = CongratsBottomSheet()
//            bottomSheetDialog.show(supportFragmentManager,"Test")
        }
        binding.backBtn.setOnClickListener{
            finish()
        }
//        get user detail from firebase
        val intent = intent
        foodName = intent.getStringArrayListExtra("foodItemName") as ArrayList<String>
        foodPrice = intent.getStringArrayListExtra("foodPrice") as ArrayList<String>
        foodDescription = intent.getStringArrayListExtra("foodDescription") as ArrayList<String>
        foodImage= intent.getStringArrayListExtra("foodImage") as ArrayList<String>
        foodIngredient = intent.getStringArrayListExtra("foodIngredient") as ArrayList<String>
        foodQuantities = intent.getIntegerArrayListExtra("foodQuantities") as ArrayList<Int>

        totalAmount = "$" + calTotalAmount().toString()
//        binding.totalAmount.isEnabled = false
        binding.totalAmount.setText(totalAmount)
    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid?:""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetails").push().key
        val orderDetails = OrderDetails(userId,name,foodName,foodImage,foodPrice,foodQuantities,address,totalAmount,phone,false,false,itemPushKey,time)
        val orderRef = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderRef.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager,"Test")
//            del item from cart
            removeItemFromCart()
            addOrderToHistory(orderDetails)
        }.addOnFailureListener {
            Toast.makeText(this,"failed to order",Toast.LENGTH_SHORT).show()
        }
    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
        databaseReference.child("user").child(userId).child("BuyHistory").child(orderDetails.itemPushKey!!).setValue(orderDetails).addOnSuccessListener {

        }

    }


    private fun removeItemFromCart() {
        val cartItemsRef = databaseReference.child("user").child(userId).child("CartItems")
        cartItemsRef.removeValue()
    }

    private fun calTotalAmount(): Int {
        var totalAmount = 0
        for( i in 0 until foodPrice.size ){
            var price = foodPrice[i]
            val lastChar  = price.last()
            val priceIntValue  = if(price.startsWith('$')) {
                price.substring(1).toInt()
            }else {
                price.toInt()
            }

            var quantity = foodQuantities[i]
            totalAmount += priceIntValue*quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user!= null) {
            val userId = user.uid
            val userReference = databaseReference.child("user").child(userId)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val names: String  = snapshot.child("name").getValue(String::class.java)?:"" // tra ve "" neu null
                    val addresses: String  = snapshot.child("address").getValue(String::class.java)?:""
                    val phones: String  = snapshot.child("phone").getValue(String::class.java)?:""
                    binding.apply {
                        nameEdt.setText(names)
                        addressEdt.setText(addresses)
                        phoneEdt.setText(phones)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

    }
}