package com.reihanalavi.mvpbarclays.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.reihanalavi.mvpbarclays.R
import com.reihanalavi.mvpbarclays.databinding.FragmentMatchDetailOverviewBindingImpl
import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.presenters.MatchOverviewPresenter
import com.reihanalavi.mvpbarclays.views.MatchOverviewView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import org.jetbrains.anko.AnkoLogger
import retrofit2.Retrofit

/**
 * A simple [Fragment] subclass.
 */
class MatchDetailOverviewFragment : Fragment(), MatchOverviewView, AnkoLogger {

    var pastUid: Int? = 0

    lateinit var retrofit: Retrofit
    lateinit var apiRepository: ApiRepository
    lateinit var presenter: MatchOverviewPresenter
    lateinit var application: Application

    lateinit var dataBinding: FragmentMatchDetailOverviewBindingImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_detail_overview, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            pastUid = it.getInt("overview")
        }

        retrofit = RetrofitBuilder.getRetrofit()
        apiRepository = retrofit.create(ApiRepository::class.java)
        application = Application()
        presenter = MatchOverviewPresenter(this, apiRepository, application)

        presenter.getPastDetailsFromDatabase(pastUid)

        Log.d("OVERVIEW PAST UID", pastUid.toString())

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onAlert(message: String, title: String) {

    }

    override fun onError(error: String) {
        Log.d("ERROR OVERVIEW", error)
    }

    override fun onResultOverview(data: Pasts?) {
        dataBinding.pasts = data
        presenter.getTeamDetailsFromDatabase(dataBinding.pasts?.idHomeTeam.toString(), "home")
        presenter.getTeamDetailsFromDatabase(dataBinding.pasts?.idAwayTeam.toString(), "away")
    }

    override fun onResultTeam(data: Teams?, side: String) {
        when(side) {
            "home" -> dataBinding.home = data
            "away" -> dataBinding.away = data
        }
    }
}
