package com.example.foodorderingapp.model

data class CartItem(
    val foodName:String?= null,
    val foodPrice:String?= null,
    val foodDescription:String?= null,
    val foodImage:String?= null,
    val foodQuantity:Int?= null,
    val foodIngredient:String?= null
)
