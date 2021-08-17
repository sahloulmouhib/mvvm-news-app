package com.androiddevs.mvvmnewsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(
    tableName ="articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    // some of them can be null that's why we added ?

    val id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String? ="https://mcleansmartialarts.com/wp-content/uploads/2017/04/default-image-620x600.jpg",
    val urlToImage: String?

) : Serializable

/*
@Entity(
    tableName ="lastArticles"
)
data class LatestArticle(
    @PrimaryKey(autoGenerate = true)
    // some of them can be null that's why we added ?
    val id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?


) : Serializable

*/