package com.reihanalavi.mvpbarclays.presenters

import android.app.Application
import android.content.Context
import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.room.BaseViewModel
import com.reihanalavi.mvpbarclays.room.TeamsDatabase
import com.reihanalavi.mvpbarclays.utils.SharedPreferencesHelper
import com.reihanalavi.mvpbarclays.views.PastsListView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

class PastsListPresenter(
    val view: PastsListView,
    var apiRepository: ApiRepository,
    application: Application,
    var context: Context
) : AnkoLogger, BaseViewModel(application) {

    lateinit var compositeDisposable: CompositeDisposable

    private var preferencesHelper = SharedPreferencesHelper(context)
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    fun refresh(idLeague: String, round: String, season: String) {
        val updateTime = preferencesHelper.getUpdateTime("past")
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getPastsFromDatabase()
        } else {
            getPastsFromServer(idLeague, round, season, context)
        }
    }

    fun refreshCache(idLeague: String, round: String, season: String) {
        getPastsFromServer(idLeague, round, season, context)
    }

    fun getPastsFromDatabase() {
        view.showLoading()

        launch {
            val pasts = TeamsDatabase(context).pastsDao().getPasts()
            retrievePasts(pasts)

            view.hideLoading()
            view.onAlert("Pasts retrieved from the database", "Success")
        }
    }

    fun getPastsFromServer(idLeague: String, round: String, season: String, context: Context) {
        view.showLoading()

        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
            apiRepository
                .getPasts(idLeague, round, season)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        view.hideLoading()
                        view.onAlert("Pasts retrieved from the server", "Success")

                        storePastsLocally(it.events, context)
                    },
                    {
                        view.hideLoading()
                        view.onError(it.message!!)

                        getPastsFromDatabase()

                        it.printStackTrace()
                    }
                )
        )
    }

    private fun retrievePasts(responses: List<Pasts>?) {
        view.onResult(responses)
    }

    private fun storePastsLocally(responses: List<Pasts>?, context: Context) {
        launch {
            val dao = TeamsDatabase(context).pastsDao()
            dao.deletePasts()
            responses?.toTypedArray()?.let {
                dao.insertPasts(*it)
            }

            var i = 0
            while (i < responses?.size!!) {
                val idGetAfterInsert = dao.getPastById(responses[i].idEvent.toString())
                responses[i].uid = idGetAfterInsert.uid
                ++i
            }
            retrievePasts(responses)
        }
        preferencesHelper.saveUpdateTime("past", System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}