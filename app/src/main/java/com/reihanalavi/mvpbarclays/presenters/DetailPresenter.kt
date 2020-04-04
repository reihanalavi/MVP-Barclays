package com.reihanalavi.mvpbarclays.presenters

import com.reihanalavi.mvpbarclays.views.DetailView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger

class DetailPresenter(val view: DetailView, var apiRepository: ApiRepository): AnkoLogger {

    lateinit var compositeDisposable: CompositeDisposable

    fun getTeamDetails(idTeam: String) {

        view.showLoading()

        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
            apiRepository
                .getTeamDetails(idTeam)
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

                    }
                )
        )

    }

}