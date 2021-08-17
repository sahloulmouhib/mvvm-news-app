package com.androiddevs.mvvmnewsapp.db
import androidx.lifecycle.LiveData
import androidx.room.*
import com.androiddevs.mvvmnewsapp.models.Article
//import com.androiddevs.mvvmnewsapp.models.LatestArticle


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article : Article): Long

    @Query("SELECT * FROM articles")
    // not a suspend function because it does not work with a live data object
    fun getAllArticles() : LiveData<List<Article>>
    // live data will notify the fragment whenever there is a fragment


    @Delete
    suspend fun deleteArticle(article: Article)






    //lastest articles
/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(latestArticle : LatestArticle): Long

    @Query("SELECT * FROM lastArticles")
    // not a suspend function because it does not work with a live data object
    fun getAllLatestArticles() : LiveData<List<LatestArticle>>
    // live data will notify the fragment whenever there is a fragment


    @Delete
    suspend fun deleteLatestArticle(article: LatestArticle)
    */

}