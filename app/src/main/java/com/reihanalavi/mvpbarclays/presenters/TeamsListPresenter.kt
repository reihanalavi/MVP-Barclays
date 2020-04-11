package com.reihanalavi.mvpbarclays.presenters

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.room.BaseViewModel
import com.reihanalavi.mvpbarclays.room.TeamsDatabase
import com.reihanalavi.mvpbarclays.utils.SharedPreferencesHelper
import com.reihanalavi.mvpbarclays.views.TeamsListView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.defaultSharedPreferences

class TeamsListPresenter(val view: TeamsListView, var apiRepository: ApiRepository, application: Application, var context: Context): AnkoLogger, BaseViewModel(application) {

    lateinit var compositeDisposable: CompositeDisposable

    private var prefHelper = SharedPreferencesHelper(context)
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    fun refresh(league: String) {
        val updateTime = prefHelper.getUpdateTime("teams")
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getTeamsFromDatabase()
        } else {
            getTeamsFromServer(league, context)
        }
    }

    fun refreshCache(league: String) {
        getTeamsFromServer(league, context)
    }

    fun searchTeamsByNameFromDatabase(teamName: String) {
        launch {
            val teams = TeamsDatabase(context).teamsDao().searchTeamsByName(teamName)
            retrieveTeams(teams)

            view.hideLoading()
        }
    }

    fun getTeamsByNameFromDatabase(teamName: String) {
        launch {
            val teams = TeamsDatabase(context).teamsDao().getTeamsByName(teamName)
            retrieveTeams(teams)

            view.hideLoading()
        }
    }

    fun getTeamsFromDatabase() {
        view.showLoading()

        launch {
            val teams = TeamsDatabase(context).teamsDao().getTeams()
            retrieveTeams(teams)

            view.hideLoading()
            view.onAlert("Teams retrieved from the database", "Success")
        }
    }

    fun getTeamsFromServer(league: String, context: Context) {
        view.showLoading()

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

                    storeTeamsLocally(it.teams, context)
                },
                {
                    view.hideLoading()
                    view.onError(it.message!!)

                    getTeamsFromDatabase()

                    it.printStackTrace()
                })
        )

    }

    private fun retrieveTeams(responses: List<Teams>?) {
        view.onResult(responses)
    }

    private fun storeTeamsLocally(responses: List<Teams>?, context: Context) {
        launch {
            val dao = TeamsDatabase(context).teamsDao()
//            dao.getTeams()
            dao.deleteTeams()
            responses?.toTypedArray()?.let { dao.insertTeams(*it) }

            var i = 0
            while (i < responses?.size!!) {
                val idGetAfterInsert = dao.getTeamById(responses[i].idTeam.toString())
                responses[i].uid = idGetAfterInsert.uid
//                responses[i].uid = i
                Log.d("RESPONSES LOCAL UID", responses[i].uid.toString())
                ++i
            }
            retrieveTeams(responses)
        }
        prefHelper.saveUpdateTime("teams", System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}