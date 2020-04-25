package com.example.coronawatch.ui.articles

import Article
import android.annotation.SuppressLint
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.coronawatch.Commentaire
import com.example.coronawatch.adapter.ArticlesAdapter
import com.example.coronawatch.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.okhttp.Callback
import  com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import org.json.JSONArray
import org.json.JSONObject

import java.io.IOException
import java.lang.NullPointerException

class ArticlesFragment : Fragment() {

    private lateinit var articlesViewModel: ArticlesViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var articlesFeed = ArticlesFeed(arrayListOf())
    private var adapter = ArticlesAdapter(articlesFeed, this.context)
    val url = "https://cb763c47.ngrok.io/articles/articles"
    val token = "5dfd1c7e93af18c660fa6b297999bb5c3b0e9e39"
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        articlesViewModel =
            ViewModelProviders.of(this).get(ArticlesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_articles, container, false)

        recyclerView = root.findViewById(R.id.recycler_view_articles) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout) as SwipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {

            adapter.articlesFeed.articles.clear()


            fetchJSON(url, token)
            adapter.notifyDataSetChanged()


        }

        fetchJSON(url, token)


        return root
    }

    private fun fetchJSON(url: String, token: String) {
        swipeRefreshLayout.isRefreshing = true
        val url = url


        val token = "Token $token"

        val request = Request.Builder().url(url).header("Authorization", token)
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(request: Request?, e: IOException?) {
                swipeRefreshLayout.isRefreshing = false
                print("Failed to execute request")
            }

            override fun onResponse(response: Response?) {
                val body = response?.body()?.string()

                try {
                    val json = JSONArray(body)


                    activity?.runOnUiThread {

                        var i = json.length() - 1

                        while (i >= 0) {

                            val jsonArticle = json.getJSONObject(i)
                            println(jsonArticle)
                            if (jsonArticle.getString("validerAR") == "true") {


                                val stringPhoto = jsonArticle.getString("photos")
                                println(stringPhoto)

                                val jsonPhoto = JSONArray(stringPhoto)
                                var j = 0
                                var imagesArrayList = arrayListOf<String>()
                                while (j < jsonPhoto.length()) {
                                    try {
                                        val image = JSONObject(jsonPhoto[j].toString())
                                        imagesArrayList.add(image["lienPhAc"].toString())
                                    } catch (e: NullPointerException) {

                                    }

                                    j++
                                }

                                println(jsonArticle["contenuAr"].toString())
                                articlesFeed.articles.add(
                                    Article(
                                        "user name",
                                        jsonArticle["contenuAr"].toString(),
                                        imagesArrayList
                                        ,
                                        parentFragment?.context
                                        ,
                                        jsonArticle["dateAr"].toString()
                                        ,
                                        arrayListOf(
                                            Commentaire("commentaire 1", "now"),
                                            Commentaire("commentaire 2", "now")


                                        )
                                    )
                                )
                            }

                            i--
                        }
                        adapter.notifyDataSetChanged()
                        swipeRefreshLayout.isRefreshing = false

                    }
                } catch (e: org.json.JSONException) {

                    view?.let {
                        Snackbar.make(it, "حدث خطأ في الإتصال ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }


                    println("حدث خطأ في الإتصال ")
                }


            }


        })

    }

}

class ArticlesFeed(val articles: ArrayList<Article>)

