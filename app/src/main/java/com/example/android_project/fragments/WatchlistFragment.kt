package com.example.android_project.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_project.R
import com.example.android_project.adapters.WatchlistAdapter
import com.example.android_project.data.WatchlistRepo
import com.example.android_project.models.WatchlistAPIRequest
import com.example.android_project.models.WatchlistItemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WatchlistFragment: Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var headerTextView: TextView
    private val watchlistItems = ArrayList<WatchlistItemModel>()

    private val adapter by lazy {
        WatchlistAdapter(watchlistItems)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_watchlist, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        headerTextView = view.findViewById(R.id.tv_watchlist_title)
        val goToMoviesButton = view.findViewById<Button>(R.id.add_movies_btn)
        goToMoviesButton.setOnClickListener { goToMovies() }

        setupRecyclerView()
        getData()
    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(context)

        view?.findViewById<RecyclerView>(R.id.rv_watchlist)?.apply {
            this.layoutManager = layoutManager
            this.adapter = this@WatchlistFragment.adapter
        }
    }

    private fun getData() {
        val userId = auth.currentUser?.uid
        userId?.let { uid ->
            db.collection("data").document(uid).get()
                .addOnSuccessListener { document ->
                    println(document.data)
                    val data = document.toObject(WatchlistAPIRequest::class.java)
                    data?.let { req ->
                        updateHeader(req.forename, req.surname)
                        updateRecyclerView(req.watchlist)
                    }
                }
        }
    }

    private fun updateHeader(forename: String, surname: String) {
        val headerText = "$forename $surname's Watchlist"
        headerTextView.text = headerText
    }

    private fun updateRecyclerView(watchlist: List<WatchlistItemModel>) {
        watchlist.forEach { watchlistItem ->
            insertWatchlistItemToDB(watchlistItem)
            watchlistItems.add(watchlistItem)
        }
        adapter.notifyItemRangeInserted(0, watchlistItems.size)
    }

    private fun goToMovies() {
        val action = WatchlistFragmentDirections.actionWatchlistFragmentToMoviesFragment()
        findNavController().navigate(action)
    }

    private fun insertWatchlistItemToDB(watchlistItem: WatchlistItemModel) {
        WatchlistRepo.insert(watchlistItem) {
            println("Product successfully inserted")
        }
    }
}