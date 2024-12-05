package com.reddit.image.saver.model

data class Post(
    var id: Int,
    val author: String,
    val title: String,
    val dateAdded: String,
    val thumbnail: String,
    val imageUrl: String,
    val commentsCounter: String
)