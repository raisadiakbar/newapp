package dev.rakamin.newapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dev.rakamin.newapp.R
import dev.rakamin.newapp.model.Article

class NewsAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val articles: MutableList<Article> = mutableListOf()

    fun setData(data: List<Article>) {
        articles.clear()
        articles.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(article: Article) {
            titleTextView.text = article.title
            descriptionTextView.text = article.description

            if (article.imageUrl.isNullOrEmpty()) {
                imageView.setImageResource(R.drawable.placeholder)
            } else {
                loadGlideImage(imageView, article.imageUrl)
            }
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val article = articles[position]
                listener.onItemClick(article, position)
            }
        }

        private fun loadGlideImage(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView)
                .load(imageUrl)
                .apply(RequestOptions().centerCrop())
                .into(imageView)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(article: Article, position: Int)
    }

}
