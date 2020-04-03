package com.reihanalavi.mvpbarclays.presenters

import android.annotation.SuppressLint
import com.reihanalavi.mvpbarclays.views.MainView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

class MainPresenter(val view: MainView): AnkoLogger {

    private val retrofit = RetrofitBuilder.getRetrofit()
    private val apiRepository = retrofit.create(ApiRepository::class.java)

    @SuppressLint("CheckResult")
    fun getTeams(league: String) {
        view.showLoading()

        debug { apiRepository.getTeams(league) }
        apiRepository.getTeams(league)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.hideLoading()
                    view.onResult(it)
                    debug { it }
                },
                {
                    view.hideLoading()
                    view.onError(it.message!!)
                })
    }
}