package com.example.instagramclone.Models

class User(private var name: String?, private var email: String?, private var username: String?, private var id: String?, private var imgUrl: String?) {

    fun getName(): String? {return name}

    fun getEmail(): String? {return email}

    fun getUsername(): String? {return username}

    fun getId(): String? {return id}

    fun getImgUrl(): String? {return imgUrl}

    fun setName(name: String?) {this.name = name}

    fun setEmail(email: String?) {this.email = email}

    fun setUsername(username: String?) {this.username = username}

    fun setId(id: String?) {this.id = id}

    fun setImgUrl(imgUrl: String?) {this.imgUrl = imgUrl}
}