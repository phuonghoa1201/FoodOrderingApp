package com.example.foodorderingapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderingapp.DetailActivity
import com.example.foodorderingapp.databinding.MenuItemBinding
import com.example.foodorderingapp.model.MenuItem


class MenuAdapter(
    private var menuItems:List<MenuItem>,
    private val requiredContext : Context
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

//    private val itemClickListener:OnClickListener ?= null
    inner class MenuViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                val position  = adapterPosition
                if(position != RecyclerView.NO_POSITION) {
//                    itemClickListener?.onItemClick(position)
                    openDetailsActivity(position)
                }
//                // set on click listener to open details
//                val intent = Intent(requiredContext, DetailActivity::class.java)
//                intent.putExtra("MenuItemName",menuItems.get(position))
//                intent.putExtra("MenuItemImage",menuItemImage.get(position))
//                requiredContext.startActivity(intent)
            }
        }

        private fun openDetailsActivity(position: Int) {
            val menuItem = menuItems[position]
//            a intent to open details activity and pass data
            val intent = Intent(requiredContext,DetailActivity::class.java).apply {
                putExtra("menuItemName",menuItem.foodName)
                putExtra("menuItemImage",menuItem.foodImage)
                putExtra("menuItemDescription",menuItem.foodDescription)
                putExtra("menuItemIngredients",menuItem.foodIngredient)
                putExtra("menuItemPrice",menuItem.foodPrice)
            }
//            start details Activity
            requiredContext.startActivity(intent)



        }
//      set data into recyclerView items: name,price,image
        fun bind(position:Int) {
            val menuItem =menuItems[position]
            binding.apply {
                menuFoodName.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice
                val  uri = Uri.parse(menuItem.foodImage)
                Glide.with(requiredContext).load(uri).into(menuImage)
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

    fun updateData(filteredMenuItems: MutableList<MenuItem>) {
        menuItems = filteredMenuItems
        notifyDataSetChanged()

    }
//    interface OnClickListener{
//        fun onItemClick(position: Int){}
//    }


}

