package com.example.foodorderingapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.foodorderingapp.MenuBottomSheetFragment
import com.example.foodorderingapp.R
import com.example.foodorderingapp.databinding.FragmentHomeBinding
import com.example.foodorderingapp.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.foodorderingapp.adapter.MenuAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems:MutableList<MenuItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // View Menu Button
        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager,"Test")
        }
//        Retrieve Popular MenuItem
        retrieveAndDisplayPopularItems()

        return binding.root
    }

    private fun retrieveAndDisplayPopularItems() {
//        get reference to the database
        database = FirebaseDatabase.getInstance()
        val foodRef: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()
//        retrieve menu items from the database
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children){
                    val menuItem = foodSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        menuItems.add(it)
                    }
//                    display popular menuItem
                    randomPopularItem()


                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun randomPopularItem() {
//        create as shuffled list of menu items
        val index = menuItems.indices.toList().shuffled()
        val numItemToShow = 6
        val subsetMenuItems = index.take(numItemToShow).map { menuItems[it] }

        setPopularItemsAdapter(subsetMenuItems)


    }

    private fun setPopularItemsAdapter(subsetMenuItems: List<MenuItem>) {
        val adapter = MenuAdapter(subsetMenuItems,requireContext())
        val layoutManager = LinearLayoutManager(requireContext())
        binding.popularRecyclerView.layoutManager = layoutManager
        binding.popularRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup image slider
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.menu_home_3, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.menu_home_4, ScaleTypes.FIT))
        binding.imageSlider.setImageList(imageList)
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                // Handle double click event
                // TODO: Implement your logic here
            }

            override fun onItemSelected(position: Int) {
                // Show a Toast when an image is selected
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })

        // Setup data for RecyclerView
//        val foodName = listOf("Pho Bo Ha Noi", "Sushi", "Basic dishes", "Pizza")
//        val price = listOf("20.22$", "15.67$", "30.00$", "21.31$")
//        val popularFoodImages = listOf(R.drawable.pho, R.drawable.sushi, R.drawable.set_vnese_food, R.drawable.menu_home_4)
//        val adapter = PopularAdapter(foodName, price, popularFoodImages,requireContext())

        // Setup RecyclerView
//        binding.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.popularRecyclerView.adapter = adapter


        // Setup RecyclerView
//        val layoutManager = GridLayoutManager(requireContext(), 2) // 2 cột
//        binding.popularRecyclerView.layoutManager = layoutManager
//        binding.popularRecyclerView.adapter = adapter

    }
}
