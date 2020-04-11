package com.reihanalavi.mvpbarclays.fragments

import android.app.ActionBar
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.reihanalavi.mvpbarclays.R
import com.reihanalavi.mvpbarclays.adapters.TeamsAdapter
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.presenters.TeamsListPresenter
import com.reihanalavi.mvpbarclays.views.TeamsListView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import kotlinx.android.synthetic.main.fragment_teams_list.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.toast
import retrofit2.Retrofit

/**
 * A simple [Fragment] subclass.
 */
class TeamsListFragment : Fragment(), TeamsListView, AnkoLogger {

    var teams: MutableList<Teams> = mutableListOf()

    lateinit var retrofit: Retrofit
    lateinit var apiRepository: ApiRepository

    lateinit var presenter: TeamsListPresenter
    lateinit var adapter: TeamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Teams"

        val application = Application()
        val viewContext = view.context

        retrofit = RetrofitBuilder.getRetrofit()
        apiRepository = retrofit.create(ApiRepository::class.java)
        presenter = TeamsListPresenter(this, apiRepository, application, viewContext)

        view.recyclerView_fragment_teams.layoutManager = LinearLayoutManager(context)
        adapter = TeamsAdapter(viewContext, teams) {
            Log.d("UID DATABASE ITEM", it.uid.toString())

            val action = TeamsListFragmentDirections.teamsListToTeamsDetail()
            action.teamUid = it.uid
            Navigation.findNavController(view).navigate(action)

        }
        adapter.notifyDataSetChanged()
        view.recyclerView_fragment_teams.adapter = adapter

        val league = "English Premier League"
        Log.d("QUERY GAIS", league)

        presenter.refresh(league)


        view.swipeRefresh_fragment_teams.onRefresh {
            presenter.refreshCache(league)
        }

        view.searchView_fragment_teams.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    if(it.isEmpty()) {
                        //not searching
                        presenter.getTeamsFromDatabase()
                    } else {
                        presenter.getTeamsByNameFromDatabase(it)
                    }
                    Log.d("SEARCH SUBMIT", it)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {
                    if(it.isEmpty()) {
                        //not searching
                        presenter.getTeamsFromDatabase()
                    } else {
                        presenter.searchTeamsByNameFromDatabase(it)
                    }
                    Log.d("SEARCH CHANGE", it)
                }
                return false
            }
        })

    }

    override fun showLoading() {
        view?.recyclerView_fragment_teams?.visibility = View.INVISIBLE
        view?.progressBar_fragment_teams?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        view?.recyclerView_fragment_teams?.visibility = View.VISIBLE
        view?.progressBar_fragment_teams?.visibility = View.INVISIBLE
        view?.swipeRefresh_fragment_teams?.isRefreshing = false
    }

    override fun onAlert(message: String, title: String) {
        toast(message)
    }

    override fun onError(error: String) {
        Log.d("ERROR TEAMS LIST", error)
    }

    override fun onResult(data: List<Teams>?) {
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
