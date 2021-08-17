package com.androiddevs.mvvmnewsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(
    tableName ="articles"
)
data class FakeArticle(
    @PrimaryKey(autoGenerate = true)
    // some of them can be null that's why we added ?
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
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