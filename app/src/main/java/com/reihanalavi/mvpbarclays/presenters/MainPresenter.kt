package com.reihanalavi.mvpbarclays.presenters

import android.annotation.SuppressLint
import android.os.Debug
import android.util.Log
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.models.TeamsResponse
import com.reihanalavi.mvpbarclays.views.MainView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.doAsync
import retrofit2.Retrofit

class MainPresenter(val view: MainView, var apiRepository: ApiRepository): AnkoLogger {

    lateinit var compositeDisposable: CompositeDisposable

    fun getTeams(league: String) {
        view.showLoading()

        debug { apiRepository.getTeams(league) }
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
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
                })
        )

    }
}