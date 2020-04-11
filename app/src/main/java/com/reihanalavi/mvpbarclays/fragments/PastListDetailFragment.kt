package com.reihanalavi.mvpbarclays.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayout

import com.reihanalavi.mvpbarclays.R
import com.reihanalavi.mvpbarclays.adapters.MatchFragmentAdapter
import com.reihanalavi.mvpbarclays.databinding.FragmentPastListDetailBinding
import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.presenters.PastsListDetailPresenter
import com.reihanalavi.mvpbarclays.views.PastListDetailView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import kotlinx.android.synthetic.main.fragment_past_list_detail.*
import kotlinx.android.synthetic.main.fragment_past_list_detail.view.*
import retrofit2.Retrofit

/**
 * A simple [Fragment] subclass.
 */
class PastListDetailFragment : Fragment(), PastListDetailView {

    lateinit var adapter: MatchFragmentAdapter
    var pastUid: Int = 0

    lateinit var dataBinding: FragmentPastListDetailBinding
    lateinit var retrofit: Retrofit
    lateinit var apiRepository: ApiRepository
    lateinit var presenter: PastsListDetailPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_past_list_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            pastUid = PastListDetailFragmentArgs.fromBundle(it).pastUid
            Log.d("PAST LIST DETAIL UID", pastUid.toString())
        }

        val application = Application()

        retrofit = RetrofitBuilder.getRetrofit()
        apiRepository = retrofit.create(ApiRepository::class.java)
        presenter = PastsListDetailPresenter(this, apiRepository, application)

        adapter = activity?.supportFragmentManager?.let { MatchFragmentAdapter(it) }!!
        addOverview(pastUid)
        addLineups(pastUid)
        view.viewPager_past.adapter = adapter
        view.tabLayout_past.setupWithViewPager(viewPager_past)

/*
        view.tabLayout_past.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.position) {
                    0 -> {
                        //val action = PastListDetailFragmentDirections.pastDetailToOverview()
                        //action.pastUid = pastUid
                        //Navigation.findNavController(view).navigate(action)
                    }
                    1 -> {
                        //val action = PastListDetailFragmentDirections.pastDetailToOverview()
                        //action.pastUid = pastUid
                        //Navigation.findNavController(view).navigate(action)
                    }
                }
            }
        })
*/

        presenter.getPastDetailsFromDatabase(pastUid)
    }

    fun addOverview(pastUid: Int) {
        val fragment = MatchDetailOverviewFragment()
        val bundle = Bundle()
        bundle.putInt("overview", pastUid)
        fragment.arguments = bundle

        adapter.addFragment(fragment, "Overview")
    }

    fun addLineups(pastUid: Int) {
        val fragment = MatchDetailLineupsFragment()
        val bundle = Bundle()
        bundle.putInt("lineups", pastUid)
        fragment.arguments = bundle

        adapter.addFragment(fragment, "Lineups")
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onAlert(message: String, title: String) {

    }

    override fun onError(error: String) {

    }

    override fun onResult(data: Pasts?) {
        dataBinding.pasts = data

        activity?.title = "${dataBinding.pasts?.strHomeTeam.toString()} - ${dataBinding.pasts?.strAwayTeam.toString()}"
    }

}
