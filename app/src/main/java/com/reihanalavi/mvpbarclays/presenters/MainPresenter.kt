package com.reihanalavi.mvpbarclays.presenters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.room.BaseViewModel
import com.reihanalavi.mvpbarclays.room.SharedPreferencesHelper
import com.reihanalavi.mvpbarclays.room.TeamsDatabase
import com.reihanalavi.mvpbarclays.views.MainView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import kotlin.coroutines.CoroutineContext

class MainPresenter(val view: MainView, var apiRepository: ApiRepository, application: Application): AnkoLogger, BaseViewModel(application) {

    lateinit var compositeDisposable: CompositeDisposable

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    fun refresh(league: String) {
        val updateTime = prefHelper.getUpdateTime()

        if(updateTime != null && updateTime != 0L) {
            if(System.nanoTime() - updateTime < refreshTime) {
                //fetch local
                getTeamsFromDatabase(league)
            } else {
                //fetch server
                getTeamsFromServer(league)
            }
        }
    }

    fun refreshCache(league: String) {
        getTeamsFromServer(league)
    }

    fun getTeamsFromDatabase(league: String) {
        view.showLoading()

        launch {
            val teams = TeamsDatabase(getApplication()).teamsDao().getTeams()
            retrieveTeams(teams)

            view.hideLoading()
            view.onAlert("Teams retrieved from the database", "Success")
        }
    }

    fun getTeamsFromServer(league: String) {
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
                    view.onAlert("Teams retrieved from the server", "Success")

                    storeTeamsLocally(it.teams)
                },
                {
                    view.hideLoading()
                    view.onError(it.message!!)

                    it.printStackTrace()
                })
        )

    }

    private fun retrieveTeams(responses: List<Teams>?) {
        view.onResult(responses)
    }

    private fun storeTeamsLocally(responses: List<Teams>?) {
        launch {
            val dao = TeamsDatabase(getApplication()).teamsDao()
            dao.deleteTeams()

            responses?.toTypedArray()?.let { dao.insertTeams(*it) }

            var i = 0
            while (i < responses?.size!!) {
                responses[i].uid = i
                ++i
            }
            retrieveTeams(responses)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}