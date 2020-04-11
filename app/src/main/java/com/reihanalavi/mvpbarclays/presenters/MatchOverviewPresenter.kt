package com.reihanalavi.mvpbarclays.presenters

import android.app.Application
import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.room.BaseViewModel
import com.reihanalavi.mvpbarclays.room.TeamsDatabase
import com.reihanalavi.mvpbarclays.views.MatchOverviewView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger

class MatchOverviewPresenter(val view: MatchOverviewView, val apiRepository: ApiRepository, application: Application): AnkoLogger, BaseViewModel(application) {

    fun getPastDetailsFromDatabase(pastUid: Int?) {
        view.showLoading()

        launch {
            val past = TeamsDatabase(getApplication()).pastsDao().getPastByUid(pastUid!!)
            retrievePast(past)
            view.onAlert("Past details retrieved from the database", "Success")
        }
    }

    fun getTeamDetailsFromDatabase(idTeam: String, side: String) {
        view.showLoading()
        launch {
            val team = TeamsDatabase(getApplication()).teamsDao().getTeamById(idTeam)
            retrieveTeam(team, side)
            view.onAlert("Past details retrieved from the database", "Success")
        }
    }

    private fun retrievePast(responses: Pasts) {
        view.onResultOverview(responses)
    }

    private fun retrieveTeam(responses: Teams, side: String) {
        view.onResultTeam(responses, side)
    }

}