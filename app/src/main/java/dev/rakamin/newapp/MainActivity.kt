package dev.rakamin.newapp;

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.rakamin.newapp.R
import dev.rakamin.newapp.adapter.NewsAdapter
import dev.rakamin.newapp.model.NewsResponse
import dev.rakamin.newapp.network.NewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    private val BASE_URL = "https://newsapi.org/v2/"
    private val API_KEY = "6c7e12d410ca4d1897335e5b296b437f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsRecyclerView = findViewById(R.id.newsRecyclerView)
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter()
        newsRecyclerView.adapter = newsAdapter

        fetchData()
    }

    private fun fetchData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsService = retrofit.create(NewsService::class.java)
        val call: Call<NewsResponse> = newsService.getTopHeadlines(API_KEY, "id")


        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    newsResponse?.articles?.let { articles ->
                        newsAdapter.setData(articles)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // Handle failure
                Toast.makeText(this@MainActivity, "Failed to fetch news: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
