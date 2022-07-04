package com.learning.newsapiclient.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.newsapiclient.data.model.Article
import com.learning.newsapiclient.databinding.NewsListItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val callback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemBinding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
         return differ.currentList.size
    }


    inner class NewsViewHolder(
        private val newsListItemBinding: NewsListItemBinding,
    ) :
        RecyclerView.ViewHolder(newsListItemBinding.root) {
        fun bind(article: Article) {
            newsListItemBinding.tvTitle.text = article.title
            newsListItemBinding.tvDescription.text = article.description
            newsListItemBinding.tvPublishedAt.text = article.publishedAt
            newsListItemBinding.tvSource.text = article.source.name
            Glide.with(newsListItemBinding.ivArticleImage.context).load(article.urlToImage)
                .into(newsListItemBinding.ivArticleImage)
            newsListItemBinding.root.setOnClickListener{
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    private var onItemClickListener : ((Article)->Unit)? = null
    // a setter function
     public fun setOnItemClickListener(listener : (Article)-> Unit){
         onItemClickListener = listener
     }


}