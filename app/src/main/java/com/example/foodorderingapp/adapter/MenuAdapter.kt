package com.example.foodorderingapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderingapp.DetailActivity
import com.example.foodorderingapp.databinding.MenuItemBinding


class MenuAdapter(private val menuItems:MutableList<String>,private val menuItemPrice:MutableList<String>,private val menuItemImage:MutableList<Int>,private val requiredContext : Context) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private val itemClickListener:OnClickListener ?= null
    inner class MenuViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                val position  = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(position)
                }
                // set on click listener to open details
                val intent = Intent(requiredContext, DetailActivity::class.java)
                intent.putExtra("MenuItemName",menuItems.get(position))
                intent.putExtra("MenuItemImage",menuItemImage.get(position))
                requiredContext.startActivity(intent)
            }
        }
        fun bind(position:Int) {
            binding.apply {
                menuFoodName.text = menuItems[position]
                menuPrice.text = menuItemPrice[position]
                menuImage.setImageResource(menuItemImage[position])


            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MenuViewHolder(binding)
    }

    override fun getItemCount(): Int = menuItems.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }
    interface OnClickListener{
        fun onItemClick(position: Int){}
    }


}

