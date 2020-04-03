package com.reihanalavi.mvpbarclays.presenters

import android.annotation.SuppressLint
import android.os.Debug
import android.util.Log
import com.reihanalavi.mvpbarclays.models.TeamsResponse
import com.reihanalavi.mvpbarclays.views.MainView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import retrofit2.Retrofit

class MainPresenter(val view: MainView, var apiRepository: ApiRepository): AnkoLogger {

    lateinit var compositeDisposable: CompositeDisposable

    @SuppressLint("CheckResult")
    fun getTeams(league: String) {
        view.showLoading()

        debug { apiRepository.getTeams(league) }
//        val compositeDisposable =
        apiRepository
            .getTeams(league)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    view.onResult(it.teams)
                },
                {
                    view.hideLoading()
                    view.onError(it.message!!)
                    Log.d("ERROR GAIS ERROR", it.message!!)
                })
    }
}