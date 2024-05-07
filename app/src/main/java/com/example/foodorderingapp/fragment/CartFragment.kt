package com.example.foodorderingapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.PayoutActivity
import com.example.foodorderingapp.adapter.CartAdapter
import com.example.foodorderingapp.databinding.FragmentCartBinding
import com.example.foodorderingapp.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodNames:MutableList<String>
    private lateinit var foodPrices:MutableList<String>
    private lateinit var foodDescriptions:MutableList<String>
    private lateinit var foodImageUri:MutableList<String>
    private lateinit var foodIngredients:MutableList<String>
    private lateinit var quantity:MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        retrieveCartItems()

        binding.proceedBtn.setOnClickListener {
//<<<<<<< HEAD
//            get order items details before proceeding to check out
            getOrderItemsDetails()
//            val intent = Intent(requireContext(), PayoutActivity::class.java)
//            startActivity(intent)
        }

        return binding.root
    }

    private fun getOrderItemsDetails() {
        val orderIdReference: DatabaseReference =database.reference.child("user").child(userId).child("CartItems")
        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredient = mutableListOf<String>()

//        get items Quantities
        val foodQuantities = cartAdapter.getUpdatedItemsQuantities()
        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
//                    get the cartItems to respective list
                    val orderItems  = foodSnapshot.getValue(CartItem::class.java)
//                    add items details in to list
                    orderItems?.foodName?.let {foodName.add(it)  }
                    orderItems?.foodPrice?.let {foodPrice.add(it)  }
                    orderItems?.foodDescription?.let {foodDescription.add(it)  }
                    orderItems?.foodImage?.let {foodImage.add(it)  }
                    orderItems?.foodIngredient?.let {foodIngredient.add(it)  }

                }
                orderNow(foodName,foodPrice,foodDescription,foodImage,foodIngredient,foodQuantities)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Order making failed.Please try again",Toast.LENGTH_SHORT).show()

            }
        })

    }



    private fun getOrderItemsDetail() {

        val orderIdReference: DatabaseReference = database.reference.child("user").child(userId).child("CartItems")

        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredient = mutableListOf<String>()
//        get items quantities
        val foodQuantities = cartAdapter.getUpdatedItemsQuantities()
        orderIdReference.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    // Lấy các mục giỏ hàng và thêm vào danh sách tương ứng
                    val orderItem = foodSnapshot.getValue(CartItem::class.java)
                    // Kiểm tra xem orderItem có null hay không trước khi thêm vào danh sách
                    orderItem?.foodName?.let { foodName.add(it) }
                    orderItem?.foodPrice?.let { foodPrice.add(it) }
                    orderItem?.foodDescription?.let { foodDescription.add(it) }
                    orderItem?.foodImage?.let { foodImage.add(it) }
                    orderItem?.foodIngredient?.let { foodIngredient.add(it) }
                }
                orderNow(foodName, foodPrice, foodDescription, foodImage,foodIngredient, foodQuantities )
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Order making failed. Please Try Again", Toast.LENGTH_SHORT).show()
            }

        } )

    }
    private fun orderNow(
        foodName: MutableList<String>,
        foodPrice: MutableList<String>,
        foodDescription: MutableList<String>,
        foodImage: MutableList<String>,
        foodIngredient: MutableList<String>,
        foodQuantities: MutableList<Int>) {
        if(isAdded && context != null){
            val intent = Intent(requireContext(),PayoutActivity::class.java)
            intent.putExtra("foodItemName",foodName as ArrayList<String>)
            intent.putExtra("foodPrice",foodPrice as ArrayList<String>)
            intent.putExtra("foodDescription",foodDescription as ArrayList<String>)
            intent.putExtra("foodImage",foodImage as ArrayList<String>)
            intent.putExtra("foodIngredient",foodIngredient as ArrayList<String>)
            intent.putExtra("foodQuantities",foodQuantities as ArrayList<Int>)
            startActivity(intent)
//=======
////           get order item before check out
//            getOrderItemsDetail()
////            val intent = Intent(requireContext(), PayoutActivity::class.java)
////            startActivity(intent)
//>>>>>>> 87815e79c860294d84da50a5d87cafd8bc8c0db9
        }

    }

//    private fun orderNow(foodName: MutableList<String>, foodPrice: MutableList<String>, foodDescription: MutableList<String>, foodImage: MutableList<String>, foodIngredient: MutableList<String>, foodQuantities: MutableList<Int>) {
//        if(isAdded && context!=null){
//            val intent = Intent(requireContext(), PayoutActivity::class.java)
//            intent.putExtra("FoodItemName", foodName as ArrayList<String>)
//            intent.putExtra("FoodItemPrice", foodPrice as ArrayList<String>)
//            intent.putExtra("FoodItemImage", foodImage as ArrayList<String>)
//            intent.putExtra("FoodItemDescription", foodDescription as ArrayList<String>)
//            intent.putExtra("FoodItemIngredient", foodIngredient as ArrayList<String>)
//            intent.putExtra("FoodItemQuantities", foodQuantities as ArrayList<Int>)
//            startActivity(intent)
//        }
//    }

    private fun retrieveCartItems() {
//        database reference to the Firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid?:""
        val foodRef :DatabaseReference = database.reference.child("user").child(userId).child("CartItems")
//       list to store cart items
        foodNames = mutableListOf()
        foodPrices = mutableListOf()
        foodDescriptions = mutableListOf()
        foodIngredients = mutableListOf()
        foodImageUri = mutableListOf()
        quantity = mutableListOf()
//        fetch data from the database
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children){
//                  get the cartItems object from the child node
                    val cartItems = foodSnapshot.getValue(CartItem::class.java)
//                  add cart items details to the list
                    cartItems?.foodName?.let { foodNames.add(it) }
                    cartItems?.foodPrice?.let { foodPrices.add(it) }
                    cartItems?.foodDescription?.let { foodDescriptions.add(it) }
                    cartItems?.foodImage?.let { foodImageUri.add(it) }
                    cartItems?.foodQuantity?.let { quantity.add(it) }
                    cartItems?.foodIngredient?.let { foodIngredients.add(it) }
                }
                setAdapter()
            }
            private fun setAdapter() {
//<<<<<<< HEAD
//               cartAdapter = CartAdapter(requireContext(),foodNames,foodPrices,foodImageUri,foodDescriptions,quantity,foodIngredients)
//                binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//=======
                cartAdapter = CartAdapter(requireContext(),foodNames,foodPrices,foodImageUri,foodDescriptions,quantity,foodIngredients)
                binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.cartRecyclerView.adapter = cartAdapter
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"data not fetch",Toast.LENGTH_SHORT).show()
            }
        })

    }


    companion object {

    }


}