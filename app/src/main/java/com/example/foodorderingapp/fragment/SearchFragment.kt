package com.example.foodorderingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.foodorderingapp.R
import com.example.foodorderingapp.adapter.MenuAdapter
import com.example.foodorderingapp.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private val originMenuFoodName = listOf("Pho Bo Ha Noi", "Sushi", "Basic dishes", "Pizza")
    private val originMenuPrice = listOf("20.22$", "15.67$", "30.00$", "21.31$")
    private val originMenuFoodImage = listOf(R.drawable.pho, R.drawable.sushi, R.drawable.set_vnese_food, R.drawable.menu_home_4)

    private val filteredMenuFoodName = mutableListOf<String>()
    private val filteredMenuPrice = mutableListOf<String>()
    private val filteredMenuFoodImage = mutableListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
//        adapter = MenuAdapter(
//            originMenuFoodName as MutableList<String>, originMenuPrice as MutableList<String>,
//            originMenuFoodImage as MutableList<Int>
//        )
        adapter = MenuAdapter(filteredMenuFoodName,filteredMenuPrice,filteredMenuFoodImage,requireContext())
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        // set up for search View
        setupSearchView()
        // show all menu Items
        showAll()
        return binding.root
    }

    private fun showAll() {
        filteredMenuFoodName.clear()
        filteredMenuPrice.clear()
        filteredMenuFoodImage.clear()

        filteredMenuFoodName.addAll(originMenuFoodName)
        filteredMenuPrice.addAll(originMenuPrice)
        filteredMenuFoodImage.addAll(originMenuFoodImage)

        adapter.notifyDataSetChanged()

    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }

        })
    }

    private fun filterMenuItems(query: String) {
        filteredMenuFoodName.clear()
        filteredMenuPrice.clear()
        filteredMenuFoodImage.clear()

        originMenuFoodName.forEachIndexed { i, foodName ->
            if(foodName.contains(query.toString(), ignoreCase = true)){
                filteredMenuFoodName.add(foodName)
                filteredMenuPrice.add(originMenuPrice[i])
                filteredMenuFoodImage.add(originMenuFoodImage[i])
            }
        }
        adapter.notifyDataSetChanged()
    }


}