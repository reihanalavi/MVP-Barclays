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
import com.reihanalavi.mvpbarclays.databinding.FragmentTeamsListDetailBinding
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.presenters.TeamsListDetailPresenter
import com.reihanalavi.mvpbarclays.views.TeamsListDetailView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import kotlinx.android.synthetic.main.fragment_teams_list_detail.view.*
import org.jetbrains.anko.AnkoLogger
import retrofit2.Retrofit

/**
 * A simple [Fragment] subclass.
 */
class TeamsListDetailFragment : Fragment(), TeamsListDetailView, AnkoLogger {

    var teamUid: Int = 0
    lateinit var dataBinding: FragmentTeamsListDetailBinding

    lateinit var retrofit: Retrofit
    lateinit var apiRepository: ApiRepository
    lateinit var presenter: TeamsListDetailPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_teams_list_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = Application()

        retrofit = RetrofitBuilder.getRetrofit()
        apiRepository = retrofit.create(ApiRepository::class.java)

        presenter = TeamsListDetailPresenter(this, apiRepository, application)

        arguments?.let {
            teamUid = TeamsListDetailFragmentArgs.fromBundle(it).teamUid
            presenter.getTeamDetailsFromDatabase(teamUid)
        }
    }



    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onAlert(message: String, title: String) {

    }

    override fun onError(error: String) {
        Log.d("ERROR TEAM DETAIL", error)
    }

    override fun onResult(data: Teams?) {
        dataBinding.teams = data

        activity?.title = dataBinding.teams?.strTeam.toString()
    }
}
