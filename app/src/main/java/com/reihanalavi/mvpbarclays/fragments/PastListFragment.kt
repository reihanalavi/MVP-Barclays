package com.reihanalavi.mvpbarclays.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.reihanalavi.mvpbarclays.R
import com.reihanalavi.mvpbarclays.adapters.PastsAdapter
import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.presenters.PastsListPresenter
import com.reihanalavi.mvpbarclays.views.PastsListView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import kotlinx.android.synthetic.main.fragment_past_list.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.toast
import retrofit2.Retrofit

/**
 * A simple [Fragment] subclass.
 */
class PastListFragment : Fragment(), PastsListView, AnkoLogger {

    var pasts: MutableList<Pasts> = mutableListOf()

    lateinit var retrofit: Retrofit
    lateinit var apiRepository: ApiRepository

    lateinit var presenter: PastsListPresenter
    lateinit var adapter: PastsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = "Past Match"

        val application = Application()
        val viewContext = view.context

        retrofit = RetrofitBuilder.getRetrofit()
        apiRepository = retrofit.create(ApiRepository::class.java)
        presenter = PastsListPresenter(this, apiRepository, application, viewContext)

        view.recyclerView_fragment_pasts.layoutManager = LinearLayoutManager(context)
        adapter = PastsAdapter(viewContext, pasts) {
            //go to detail
            val action = PastListFragmentDirections.pastListToPastDetail()
            action.pastUid = it.uid
            Navigation.findNavController(view).navigate(action)
        }
        adapter.notifyDataSetChanged()
        view.recyclerView_fragment_pasts.adapter = adapter

        val idLeague = "4328"
        val round = "38"
        val season = "1819"

        presenter.refresh(idLeague, round, season)

        view.swipeRefresh_fragment_pasts.onRefresh {
            presenter.refreshCache(idLeague, round, season)
        }

    }

    override fun showLoading() {
        view?.recyclerView_fragment_pasts?.visibility = View.INVISIBLE
        view?.progressBar_fragment_pasts?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        view?.recyclerView_fragment_pasts?.visibility = View.VISIBLE
        view?.progressBar_fragment_pasts?.visibility = View.INVISIBLE
        view?.swipeRefresh_fragment_pasts?.isRefreshing = false
    }

    override fun onAlert(message: String, title: String) {
        toast(message)
    }

    override fun onError(error: String) {
        Log.d("ERROR PAST MATCH", error)
    }

    override fun onResult(data: List<Pasts>?) {
        pasts.clear()

        if(data != null) {
            pasts.addAll(data)
        } else {
            toast("Kosong")
        }
        Log.d("SIZE PAST GAIS", pasts.size.toString())
        adapter.notifyDataSetChanged()
    }

}
