package com.example.foodorderingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.databinding.CartItemBinding

class CartAdapter(private val cartItems:MutableList<String>,private val cartItemPrice:MutableList<String>, private var cartImage: MutableList<Int> ): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val itemQuanties = IntArray(cartItems.size){1}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class CartViewHolder(private val binding:CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity2 = itemQuanties[position]
                cartFoodName.text = cartItems[position]
                cardItemPrice.text = cartItemPrice[position]
                cartImg.setImageResource(cartImage[position])
                quantity.text = quantity2.toString()

                minusBtn.setOnClickListener {
                    decreaseQuantity(position)

                }
                plusBtn.setOnClickListener {
                    increaseQuantity(position)

                }
                deleteBtn.setOnClickListener {
                    val itemPosition = adapterPosition
                    if(itemPosition != RecyclerView.NO_POSITION){
                        delItem(itemPosition)
                    }

                }

            }
        }
        private fun decreaseQuantity(position:Int){
            if(itemQuanties[position]>1){
                itemQuanties[position]--
                binding.quantity.text = itemQuanties[position].toString()
            }
        }
        private fun increaseQuantity(position:Int){
            if(itemQuanties[position]<10){
                itemQuanties[position]++
                binding.quantity.text = itemQuanties[position].toString()
            }
        }
        private fun delItem(position:Int) {
            cartItems.removeAt(position)
            cartItemPrice.removeAt(position)
            cartImage.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,cartItems.size)
        }
    }


}