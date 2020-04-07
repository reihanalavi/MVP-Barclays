package com.reihanalavi.mvpbarclays.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.reihanalavi.mvpbarclays.R
import com.reihanalavi.mvpbarclays.adapters.TeamsAdapter
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.presenters.MainPresenter
import com.reihanalavi.mvpbarclays.views.MainView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
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
        presenter = MainPresenter(this, apiRepository, application)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TeamsAdapter(this, teams) {
            Log.d("UID DATABASE ITEM", it.uid.toString())

            startActivity<DetailActivity>("isLocal" to true, "id" to it.uid)
//            if(it.uid.toString() != null) {
//                //it is local
//                startActivity<DetailActivity>("isLocal" to true, "id" to it.uid.toString())
//            } else {
//                //it is from the server
//                startActivity<DetailActivity>("isLocal" to false,
//                    "stadiumThumb" to it.strStadiumThumb, "stadiumName" to it.strStadium,
//                    "stadiumLoc" to it.strStadiumLocation, "teamBadge" to it.strTeamBadge,
//                    "teamDesc" to it.strDescriptionEN, "teamJersey" to it.strTeamJersey)
//            }
        }
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter

        val league = "English Premier League"
        Log.d("QUERY GAIS", league)

        swipeRefresh.onRefresh {
            presenter.refreshCache(league)
        }

        presenter.refresh(league)
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
        }.show()
    }

    override fun onError(error: String) {
        Log.d("ON ERROR GAIS", error)
        presenter.getTeamsFromDatabase()
    }

    override fun onResult(data: List<Teams>?) {
        swipeRefresh.isRefreshing = false
        teams.clear()

        if (data != null) {
            teams.addAll(data)
        } else {
            toast("Kosong")
        }
        Log.d("SIZE TEAMS GAIS", teams.size.toString())
        adapter.notifyDataSetChanged()
    }
}
