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
import com.reihanalavi.mvpbarclays.databinding.FragmentMatchDetailLineupsBindingImpl
import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.presenters.MatchLineupPresenter
import com.reihanalavi.mvpbarclays.views.MatchLineupView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import org.jetbrains.anko.AnkoLogger
import retrofit2.Retrofit

/**
 * A simple [Fragment] subclass.
 */
class MatchDetailLineupsFragment : Fragment(), MatchLineupView, AnkoLogger {

    var pastUid: Int? = 0

    lateinit var retrofit: Retrofit
    lateinit var apiRepository: ApiRepository
    lateinit var presenter: MatchLineupPresenter
    lateinit var application: Application

    lateinit var dataBinding: FragmentMatchDetailLineupsBindingImpl

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_detail_lineups, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            pastUid = it.getInt("lineups")
        }

        retrofit = RetrofitBuilder.getRetrofit()
        apiRepository = retrofit.create(ApiRepository::class.java)
        application = Application()
        presenter = MatchLineupPresenter(this, apiRepository, application)

        presenter.getPastDetailsFromDatabase(pastUid)

        Log.d("LINEUPS PAST UID", pastUid.toString())

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onAlert(message: String, title: String) {

    }

    override fun onError(error: String) {
        Log.d("ERROR LINEUPS", error)
    }

    override fun onResult(data: Pasts?, homeForm: String?, awayForm: String?) {
        dataBinding.pasts = data

        dataBinding.homeForm = homeForm
        dataBinding.awayForm = awayForm

    }
}
