package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.models.Article
//import com.androiddevs.mvvmnewsapp.models.LatestArticle

class NewsRepository(
    val db :ArticleDatabase
) {

    //suspend funs that calls the api to get the needed information
    suspend fun getBreakingNews(countryCode : String, pageNumber: Int)=RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)
    suspend fun searchNews(searchQuery: String, pageNumber:Int)=RetrofitInstance.api.searchNews(searchQuery,pageNumber)

    //removing and adding articles to the room database
    suspend fun  upsert(article : Article) = db.getArticleDao().upsert(article)
    fun getSavedNews()= db.getArticleDao().getAllArticles()
    suspend fun deleteArticle(article: Article)=db.getArticleDao().deleteArticle(article)



    /*

    //adding the latest articles to the database
    suspend fun  upsert(latestArticle : LatestArticle) = db.getArticleDao().upsert(latestArticle)
    fun getLatestNews()= db.getArticleDao().getAllLatestArticles()
    suspend fun deleteArticle(latestArticle : LatestArticle)=db.getArticleDao().deleteLatestArticle(latestArticle)

     */
}
