package com.example.vjezba2

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : ComponentActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var repoCountTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)

        recyclerView = findViewById(R.id.repoRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        repoCountTextView = findViewById(R.id.repoCountTextView)
        fetchRepos()
    }

    private fun fetchRepos() {
        val url = "https://api.github.com/search/repositories?q=stars:>100000&per_page=100"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val items = response.getJSONArray("items")
                    val repos = mutableListOf<Repo>()
                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val name = item.getString("name")
                        val stars = item.getInt("stargazers_count")
                        val avatar = item.getJSONObject("owner").getString("avatar_url")
                        repos.add(Repo(name, stars, avatar))
                    }
                    repoCountTextView.text = "Broj repozitorija: ${repos.size}"
                    recyclerView.adapter = RepoAdapter(repos)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Greška u parsiranju podataka", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                error.printStackTrace()
                Toast.makeText(this, "Greška pri dohvaćanju podataka", Toast.LENGTH_SHORT).show()
            })

        Volley.newRequestQueue(this).add(request)
    }
}
