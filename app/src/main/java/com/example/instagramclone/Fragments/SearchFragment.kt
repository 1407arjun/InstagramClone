package com.example.instagramclone.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagramclone.Adapters.UserAdapter
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private var searchList: ArrayList<User> = ArrayList()
    private lateinit var searchAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        searchRecyclerView.setHasFixedSize(true)
        searchRecyclerView.layoutManager = LinearLayoutManager(context)

        searchAdapter = UserAdapter(searchList, context, true)
        searchRecyclerView.adapter = searchAdapter
        readUsers()

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    private fun readUsers() {
        val reference: DatabaseReference = Firebase.database.reference.child("Users")
        reference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (searchEditText.text.toString().isNullOrEmpty()){
                    searchList.clear()
                    for (dataSnapshot: DataSnapshot in snapshot.children){
                        val user: User? = dataSnapshot.getValue(User::class.java)
                        if (user != null) {
                            searchList.add(user)
                        }
                    }
                    searchAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}