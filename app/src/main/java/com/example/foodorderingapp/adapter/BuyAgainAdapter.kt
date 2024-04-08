package com.example.foodorderingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.BuyAgainItemBinding

class BuyAgainAdapter(private val buyAgainFoodName: ArrayList<String>, private val
                        buyAgainFoodPrice: ArrayList<String>,private val buyAgainStatusFood: ArrayList<String>,private val buyAgainFoodImage: ArrayList<Int> ):
    RecyclerView.Adapter<BuyAgainAdapter.BuyagainViewHolder>() {

    override fun onBindViewHolder(holder: BuyagainViewHolder, position: Int) {
        holder.bind(buyAgainFoodName[position], buyAgainFoodPrice[position], buyAgainStatusFood[position], buyAgainFoodImage[position])
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyagainViewHolder {
        val binding = BuyAgainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BuyagainViewHolder(binding)
    }

    override fun getItemCount(): Int = buyAgainFoodName.size
    class BuyagainViewHolder (private val binding: BuyAgainItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(foodName: String, foodPrice: String, foodStatus: String, foodImage: Int) {
            binding.buyAgainFoodName.text = foodName
            binding.buyAgainFoodPrice.text = foodPrice
            binding.buyAgainStatusFood.text = foodStatus
            binding.buyAgainFoodImage.setImageResource(foodImage)

        }

    }



}
