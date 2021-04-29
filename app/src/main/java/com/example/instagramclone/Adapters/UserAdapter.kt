package com.example.instagramclone.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.instagramclone.Models.User
import com.example.instagramclone.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class UserAdapter(private val arrayList: ArrayList<User>, private val context: Context?, private val isFragment: Boolean?): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var searchProfileImageView: ImageView = itemView.findViewById(R.id.searchProfileImageView)
        var searchNameText: TextView = itemView.findViewById(R.id.searchNameText)
        var searchUsernameText: TextView = itemView.findViewById(R.id.searchUsernameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_cardview, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val firebaseUser: FirebaseUser? = Firebase.auth.currentUser
        val user: User = arrayList[position]

        holder.searchUsernameText.text = user.getUsername()
        holder.searchNameText.text = user.getName()
        if (context != null) {
            Glide.with(context).load(user.getImgUrl()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(CircleCrop()).placeholder(R.drawable.ic_like_heart_outline)
                .fallback(R.drawable.ic_person_outline).error(R.drawable.ic_search_outline)
                .dontAnimate().into(holder.searchProfileImageView)
        }
    }

    override fun getItemCount() = arrayList.size
}