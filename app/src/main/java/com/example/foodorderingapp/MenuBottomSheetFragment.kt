package com.example.foodorderingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.adapter.MenuAdapter
import com.example.foodorderingapp.databinding.FragmentMenuBottomSheetBinding
import com.example.foodorderingapp.model.MenuItem
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBottomSheetBinding.inflate(inflater,container,false)

//        button Back
        binding.btnBack.setOnClickListener {
            dismiss()
        }
        retrieveMenuItem()


//        val menuFoodName = listOf("Pho Bo Ha Noi", "Sushi", "Basic dishes", "Pizza")
//        val menuPrice = listOf("20.22$", "15.67$", "30.00$", "21.31$")
//        val menuFoodImages = listOf(R.drawable.pho, R.drawable.sushi, R.drawable.set_vnese_food, R.drawable.menu_home_4)
//        val adapter = MenuAdapter(
//            ArrayList(menuFoodName),
//            ArrayList(menuPrice),
//            ArrayList(menuFoodImages),requireContext()
//        )
//        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
//        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL)
//        binding.menuRecyclerView.setLayoutManager(gridLayoutManager)
//        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.menuRecyclerView.adapter = adapter
        return binding.root
    }

    private fun retrieveMenuItem() {
        database =FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf() // khoi tao ds trong, co the edit

        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(foodSnapshot in snapshot.children) {
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let{
                        menuItems.add(it)
                    }
                    Log.d("item","onDataChange:DataReceived")
//                    once data receive, set to adapter
                    setAdapter()
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    private fun setAdapter() {
        if(menuItems.isNotEmpty()) {
            val adapter = MenuAdapter(menuItems, requireContext())
            binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.menuRecyclerView.adapter = adapter
            Log.d("item","SetAdapter: data set")
        }else{
            Log.d("item","SetAdapter:data not set")
        }
    }

    companion object {

    }
}