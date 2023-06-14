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


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
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

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(article: Article) {
            titleTextView.text = article.title
            descriptionTextView.text = article.description

            // Menggunakan Glide untuk memuat gambar
            Glide.with(itemView)
                .load(article.imageUrl)
                .apply(RequestOptions().centerCrop())
                .into(imageView)
        }
    }
}
