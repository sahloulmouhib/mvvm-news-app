package com.androiddevs.mvvmnewsapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.NewsApplication
import com.androiddevs.mvvmnewsapp.models.Article
//import com.androiddevs.mvvmnewsapp.models.LatestArticle
import com.androiddevs.mvvmnewsapp.models.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel  (
    app :Application,
    val newsRepository : NewsRepository
) : AndroidViewModel(app) {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1


    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    //pagination
    var breakingNewsResponse :NewsResponse?=null
    var searchNewsResponse :NewsResponse?=null

    var newSearchQuery:String? = null
    var oldSearchQuery:String? = null


    init{

        //getBreakingNews("us")


    }

    fun getBreakingNews(countryCode: String,category: String) = viewModelScope.launch {
       /* breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))

        */
        safeBreakingNewsCall(countryCode,category)
    }

    //we need to modify it to make the pagination
    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if(breakingNewsResponse == null)
                {
                    breakingNewsResponse= resultResponse
                }
                else
                {
                    val oldArticle =breakingNewsResponse?.articles
                    val newArticle=resultResponse.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Success(breakingNewsResponse ?:resultResponse)
            }
        }
        return  Resource.Error(response.message())
    }



    fun searchNews(searchQuery: String,sortBy:  String) = viewModelScope.launch {
       /* searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))

        */
        safeSearchNewsCall(searchQuery,sortBy)
    }


    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if(searchNewsResponse == null || newSearchQuery != oldSearchQuery) {
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                } else {
                    searchNewsPage++
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    // ****Room saved article*****
    fun saveArticle(article : Article)= viewModelScope.launch {
        newsRepository.upsert(article)
    }
    //we don't need a suspend function we will just observe on this function from our fragments so we are directly notified about our changes
    fun getSavedNews() =newsRepository.getSavedNews()

    fun deleteArticle(article :Article)=viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }




    /*

    // ****Room latest article manipulation *****
    fun saveLatestArticle(latestArticle: LatestArticle)= viewModelScope.launch {
        newsRepository.upsert(latestArticle)
    }
    //we don't need a suspend function we will just observe on this function from our fragments so we are directly notified about our changes
    fun getLatestNews() =newsRepository.getSavedNews()

    fun deleteLatestArticle(latestArticle: LatestArticle)=viewModelScope.launch {
        newsRepository.deleteArticle(latestArticle)
    }
    */







    //*****error messages*******
    private suspend fun safeBreakingNewsCall(countryCode:String,category: String){
    breakingNews.postValue(Resource.Loading())
    try{
        if(hasInternetConnection())
        {
            val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage,category)
            breakingNews.postValue(handleBreakingNewsResponse(response))
        }
        else{
            breakingNews.postValue(Resource.Error("Cannot Establish Internet Connection"))

        }
    }catch (t : Throwable)
    {
            when(t)
            {
                // IOException can happen with retrofit
                is IOException-> breakingNews.postValue(Resource.Error("Network Failure"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }
    }

    }


    private suspend fun safeSearchNewsCall(searchQuery: String,sortBy:String) {
        newSearchQuery = searchQuery
        searchNews.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepository.searchNews(searchQuery, searchNewsPage,sortBy)
                searchNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchNews.postValue(Resource.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Failure"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }
    }



    //no internet handling
    private fun hasInternetConnection():Boolean
    {
        // we need a context that's why this class wil inherit from  AndroidViewModel  (we use application context)
        val connectivityManager=getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities=connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(TRANSPORT_WIFI)-> true
                capabilities.hasTransport(TRANSPORT_CELLULAR)-> true
                capabilities.hasTransport(TRANSPORT_ETHERNET)-> true
                else -> false

            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type)
                {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else ->false

                }
            }

        }
        return false
    }


}