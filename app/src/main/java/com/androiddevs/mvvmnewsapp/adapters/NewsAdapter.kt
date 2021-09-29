package com.androiddevs.mvvmnewsapp.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.models.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_article_preview2.view.*


import java.time.Duration
import java.time.LocalDateTime

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder> (){

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback =object : DiffUtil.ItemCallback<Article>()
    {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem== newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article_preview2,
            parent,
            false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply{
            Glide.with(this).load(article.urlToImage).into(ivCountryFlag)
            tvCountryName.text=article.source?.name
            tvCountryCode.text = article.title
            tvDescription.text= article.description


            tvPublishedAt.text= timeDifference(article.publishedAt?.removeRange(article.publishedAt.length-1 until article.publishedAt.length))

            setOnClickListener{
                onItemClickListener?.let {
                    it(article) }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener : (Article) -> Unit){
        onItemClickListener = listener
    }


}

fun timeDifference(date: String?):String
{
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val date2 = LocalDateTime.parse(date)
        val date1 = LocalDateTime.now()
        val diff= Duration.between(date2, date1)

        val difference= diff.toMillis()
        val days  =  (difference) / (1000*60*60*24)
        val hours =  ((difference - (1000*60*60*24*days)) / (1000*60*60))
        val  min =  (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60)
        var result=""


        if((days.toInt()==0) )
        {
            if(  (hours.toInt() ==0))
            {
                result="Just Now"
            }
            else if(hours.toInt()>0)
            {
                result="$hours hours ago"
            }
        }
        else if ((days.toInt()>0) )
        {
            if(  (hours.toInt() ==0))
            {
                result="$days days ago"
            }
            else if(hours.toInt()>0)
            {
                result="$days days & $hours hours ago"
            }
        }


       return result
    } else {
        return ""
    }


}