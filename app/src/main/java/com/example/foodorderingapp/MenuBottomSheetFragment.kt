package com.example.foodorderingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodorderingapp.adapter.MenuAdapter
import com.example.foodorderingapp.databinding.FragmentMenuBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MenuBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMenuBottomSheetBinding


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


        val menuFoodName = listOf("Pho Bo Ha Noi", "Sushi", "Basic dishes", "Pizza")
        val menuPrice = listOf("20.22$", "15.67$", "30.00$", "21.31$")
        val menuFoodImages = listOf(R.drawable.pho, R.drawable.sushi, R.drawable.set_vnese_food, R.drawable.menu_home_4)

        val adapter = MenuAdapter(
            ArrayList(menuFoodName),
            ArrayList(menuPrice),
            ArrayList(menuFoodImages)
        )

//        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
//        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL)
//        binding.menuRecyclerView.setLayoutManager(gridLayoutManager)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {

    }
}