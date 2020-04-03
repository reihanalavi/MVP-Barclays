package com.reihanalavi.mvpbarclays

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.reihanalavi.mvpbarclays.adapters.TeamsAdapter
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.models.TeamsResponse
import com.reihanalavi.mvpbarclays.presenters.MainPresenter
import com.reihanalavi.mvpbarclays.views.MainView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.alert
import org.jetbrains.anko.debug
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(), MainView, AnkoLogger {

    var teams: MutableList<Teams> = mutableListOf()

    lateinit var retrofit: Retrofit
    lateinit var apiRepository: ApiRepository

    lateinit var presenter: MainPresenter
    lateinit var adapter: TeamsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retrofit = RetrofitBuilder.getRetrofit()
        apiRepository = retrofit.create(ApiRepository::class.java)
        presenter = MainPresenter(this, apiRepository)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TeamsAdapter(this, teams) {

        }
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter

        val league = "English Premier League"
        val leagueFinal = league.replace(" ", "%20")
        Log.d("QUERY GAIS", leagueFinal)

        swipeRefresh.onRefresh {
            presenter.getTeams(leagueFinal)
        }

        presenter.getTeams(leagueFinal)
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

    override fun onResult(data: List<Teams>?) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        data?.let { teams.addAll(it) }
        Log.d("SIZE GAIS SIZE", data?.size.toString())
        adapter.notifyDataSetChanged()
    }
}
