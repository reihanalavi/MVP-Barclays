package com.reihanalavi.mvpbarclays

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.reihanalavi.mvpbarclays.adapters.TeamsAdapter
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.presenters.MainPresenter
import com.reihanalavi.mvpbarclays.views.MainView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.alert
import org.jetbrains.anko.debug
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), MainView, AnkoLogger {

    lateinit var league: String
    lateinit var presenter: MainPresenter
    lateinit var adapter: TeamsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)

        adapter = TeamsAdapter(this) {

        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        swipeRefresh.onRefresh {
            presenter.getTeams("English Premier League")
        }

        presenter.getTeams("English Premier League")
    }

    override fun showLoading() {
        toast("Loading")
    }

    override fun hideLoading() {
        toast("Selesai")
    }

    override fun onAlert(message: String, title: String) {
        alert(message, title){
            positiveButton("OK") {}
        }
    }

    override fun onError(error: String) {
        debug { error }
    }

    override fun onResult(data: ArrayList<Teams>) {
        swipeRefresh.isRefreshing = false
        adapter.teams = data
        adapter.notifyDataSetChanged()
    }
}
