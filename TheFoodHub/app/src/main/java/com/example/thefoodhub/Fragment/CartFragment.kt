package com.example.thefoodhub.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.disklrucache.DiskLruCache
import com.example.thefoodhub.CongratsBottomSheet
import com.example.thefoodhub.PayOutActivity
import com.example.thefoodhub.R
import com.example.thefoodhub.adaptar.CartAdapter
import com.example.thefoodhub.databinding.FragmentCartBinding
import com.example.thefoodhub.model.CartItems
import com.google.android.play.core.integrity.q
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var foodName: MutableList<String>
    private lateinit var foodPrice: MutableList<String>
    private lateinit var foodDescription: MutableList<String>
    private lateinit var foodImageUri: MutableList<String>
    private lateinit var foodIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        retrieveCartItems()

        binding.prossedButton.setOnClickListener {
            //get order items details before proceeding to check out
            getOrderItemsDetail()
        }


        return binding.root
    }

    private fun getOrderItemsDetail() {
        val orderIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        val foodName = mutableListOf<String>()
        val foodPrice = mutableListOf<String>()
        val foodImage = mutableListOf<String>()
        val foodDescription = mutableListOf<String>()
        val foodIngredient = mutableListOf<String>()
        // get items quantity's
        val foodQuantities = cartAdapter.getUpdatedItemQuantities()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (foodSnapshot in snapshot.children) {
                    //get the cartItems To Respective List
                    val orderItems = foodSnapshot.getValue(CartItems::class.java)
                    //add items details into list
                    orderItems?.foodName?.let { foodName.add(it) }
                    orderItems?.foodPrice?.let { foodPrice.add(it) }
                    orderItems?.foodDescription?.let { foodDescription.add(it) }
                    orderItems?.foodImage?.let { foodImage.add(it) }
                    orderItems?.foodIngredient?.let { foodIngredient.add(it) }
                }
                orderNow(
                    foodName,
                    foodPrice,
                    foodDescription,
                    foodImage,
                    foodIngredient,
                    foodQuantities
                )
            }

            private fun orderNow(
                foodName: kotlin.collections.MutableList<kotlin.String>,
                foodPrice: kotlin.collections.MutableList<kotlin.String>,
                foodDescription: kotlin.collections.MutableList<kotlin.String>,
                foodImage: kotlin.collections.MutableList<kotlin.String>,
                foodIngredient: kotlin.collections.MutableList<kotlin.String>,
                foodQuantities: kotlin.collections.MutableList<kotlin.Int>
            ) {
                if (isAdded && context != null) {
                    val intent = Intent(requireContext(), PayOutActivity::class.java)
                    intent.putExtra(
                        "FoodItemName",
                        foodName as kotlin.collections.ArrayList<String>
                    )
                    intent.putExtra(
                        "FoodItemPrice",
                        foodPrice as kotlin.collections.ArrayList<String>
                    )
                    intent.putExtra(
                        "FoodItemImage",
                        foodImage as kotlin.collections.ArrayList<String>
                    )
                    intent.putExtra(
                        "FoodItemDescription",
                        foodDescription as kotlin.collections.ArrayList<String>
                    )
                    intent.putExtra(
                        "FoodItemIngredient",
                        foodIngredient as kotlin.collections.ArrayList<String>
                    )
                    intent.putExtra(
                        "FoodItemQuantities",
                        foodQuantities as kotlin.collections.ArrayList<Int>
                    )
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Order making Failed. Please Try Again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun retrieveCartItems() {
        //database reference to the firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val foodReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")
        //list to store cart items
        foodName = mutableListOf()
        foodPrice = mutableListOf()
        foodDescription = mutableListOf()
        foodIngredients = mutableListOf()
        foodImageUri = mutableListOf()
        quantity = mutableListOf()

        //fetch data from the database
        foodReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (foodSnapshot in snapshot.children) {
                    //get the cartItems object from the child node
                    val cartItem = foodSnapshot.getValue(CartItems::class.java)
                    //add cart item details to the list
                    cartItem?.foodName?.let { foodName.add(it) }
                    cartItem?.foodPrice?.let { foodPrice.add(it) }
                    cartItem?.foodDescription?.let { foodDescription.add(it) }
                    cartItem?.foodImage?.let { foodImageUri.add(it) }
                    cartItem?.foodQuantity?.let { quantity.add(it) }
                    cartItem?.foodIngredient?.let { foodIngredients.add(it) }
                }
                setAdapter()
            }

            private fun setAdapter() {
                cartAdapter = CartAdapter(
                    requireContext(),
                    foodName,
                    foodPrice,
                    foodDescription,
                    foodImageUri,
                    quantity,
                    foodIngredients
                )
                binding.cartRecyclerView.layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL, false
                )
                binding.cartRecyclerView.adapter = cartAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data not fetch", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {

    }
}