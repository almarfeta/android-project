package com.example.android_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.example.android_project.R
import com.example.android_project.adapters.MovieAdapter
import com.example.android_project.models.MovieModel
import com.example.android_project.models.WatchlistAPIRequest
import com.example.android_project.models.WatchlistItemModel
import com.example.android_project.utils.VolleyRequestQueue
import com.example.android_project.utils.extenstions.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.JsonObject

class MoviesFragment : Fragment(), MovieAdapter.OnAddToWatchlistClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val movieList = ArrayList<MovieModel>()

    private val adapter by lazy {
        MovieAdapter(movieList, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        setupRecyclerView()

        val searchButton = view.findViewById<Button>(R.id.search_btn)
        searchButton.setOnClickListener { doSearch() }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)

        view?.findViewById<RecyclerView>(R.id.rv_movies)?.apply {
            this.layoutManager = layoutManager
            this.adapter = this@MoviesFragment.adapter
        }
    }

    private fun doSearch() {
        val searchEditText = view?.findViewById<EditText>(R.id.et_search)

        val search = when(searchEditText?.text?.isNotEmpty()) {
            true -> searchEditText.text.toString()
            else -> {
                getString(R.string.invalid_search).showToast(context)
                return
            }
        }

        movieList.clear()
        adapter.notifyDataSetChanged()
        getMovies(search)
    }

    private fun getMovies(search: String) {
        val url = "https://search.imdbot.workers.dev/?q=$search"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                handleSearchResponse(response)
            },
            {
                println("That didn't work!")
            })

        VolleyRequestQueue.addToRequestQueue(stringRequest)
    }

    private fun handleSearchResponse(json: String) {
        val gson = Gson()
        val jsonObject = gson.fromJson(json, JsonObject::class.java)
        val descriptionArray = jsonObject.getAsJsonArray("description")

        for (element in descriptionArray) {
            val item = element.asJsonObject
            val name = item.get("#TITLE").asString
            val year = item.get("#YEAR").asString
            val actors = item.get("#ACTORS").asString
            val movie = MovieModel(name, year, actors)
            movieList.add(movie)
        }

        adapter.notifyItemRangeInserted(0, movieList.size)
    }

    override fun onAddToWatchlist(movie: MovieModel) {
        val userId = auth.currentUser?.uid ?: return
        val watchlistItem = WatchlistItemModel(movie.name, movie.year, movie.actors)

        val document = db.collection("data").document(userId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(document)
            val currentWatchlist = snapshot.toObject(WatchlistAPIRequest::class.java)?.watchlist ?: arrayListOf()
            currentWatchlist.add(watchlistItem)

            transaction.update(document, "watchlist", currentWatchlist)
        }.addOnSuccessListener {
            getString(R.string.movie_added).showToast(context)
            goToWatchlist()
        }.addOnFailureListener {
            getString(R.string.movie_added_fail).showToast(context)
        }
    }

    private fun goToWatchlist() {
        movieList.clear()
        adapter.notifyDataSetChanged()

        val action = MoviesFragmentDirections.actionMoviesFragmentToWatchlistFragment()
        findNavController().navigate(action)
    }
}