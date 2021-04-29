package com.example.instagramclone.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        searchEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchUser(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                TODO("Not yet implemented")
            }

        })

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

    private fun searchUser(key: String){
        val query: Query = Firebase.database.reference.child("Users").orderByChild("username")
            .startAt(key).endAt(key + "\uf8ff")

        query.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                searchList.clear()
                for (dataSnapshot: DataSnapshot in snapshot.children){
                    val user: User? = dataSnapshot.getValue(User::class.java)
                    if (user != null) {
                        searchList.add(user)
                    }
                }
                searchAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}