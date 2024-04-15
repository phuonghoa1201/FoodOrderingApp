package com.example.foodorderingapp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.CongratsBottomSheet
import com.example.foodorderingapp.PayoutActivity
import com.example.foodorderingapp.adapter.CartAdapter

import com.example.foodorderingapp.R
import com.example.foodorderingapp.databinding.FragmentCartBinding


class CartFragment : Fragment() {
   private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)
        val cartFoodName = listOf("Burger","Hot dog","Milkshake","Fries","Taco","Pizza","Fried chicken")
        val cartFoodPrice = listOf("$5","$7","$8","$9","$4","$5.5","$6.5")
        val cartImage = listOf(
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu1,
            R.drawable.menu2,
            R.drawable.menu3,
            R.drawable.menu1
        )
        val adapter = CartAdapter(ArrayList(cartFoodName),ArrayList(cartFoodPrice),ArrayList(cartImage))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter

        binding.proceedBtn.setOnClickListener {
            val intent= Intent(requireContext(),PayoutActivity::class.java)
            startActivity(intent)
        }
//        binding.proceedBtn.setOnClickListener {
//            val bottomSheetDialog = CongratsBottomSheet()
//            bottomSheetDialog.show(parentFragmentManager,"Test")
//        }
        return binding.root
    }
    companion object{

    }


}