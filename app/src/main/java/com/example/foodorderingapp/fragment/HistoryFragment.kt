package com.example.foodorderingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapter.BuyAgainAdapter
import com.example.foodorderingapp.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(layoutInflater, container,false)
        // Inflate the layout for this fragment
        setuprecyclerView()
        return binding.root
    }
    private fun setuprecyclerView(){
        val buyAgainFoodName = arrayListOf("Sushi", "Basic Dishes", "Tacos")
        val buyAgainFoodPrice = arrayListOf("20.23$", "25.12$", "10.00$")
        val buyAgainStatusFood = arrayListOf("Order Delivered","Order Delivered","Order Delivered" )
        val buyAgainFoodImage = arrayListOf(R.drawable.sushi, R.drawable.set_vnese_food, R.drawable.menu_home_3)
        buyAgainAdapter = BuyAgainAdapter(buyAgainFoodName, buyAgainFoodPrice, buyAgainStatusFood, buyAgainFoodImage)
        binding.BuyAgainRecyclerView.adapter = buyAgainAdapter
        binding.BuyAgainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
    companion object


}