package com.reihanalavi.mvpbarclays.presenters

import android.app.Application
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.room.BaseViewModel
import com.reihanalavi.mvpbarclays.room.TeamsDatabase
import com.reihanalavi.mvpbarclays.views.TeamsListDetailView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger

class TeamsListDetailPresenter(val view: TeamsListDetailView, var apiRepository: ApiRepository, application: Application): AnkoLogger, BaseViewModel(application) {

    fun getTeamDetailsFromDatabase(uidTeam: Int) {
        view.showLoading()

        launch {
            val team = TeamsDatabase(getApplication()).teamsDao().getTeamByUid(uidTeam)
            retrieveTeam(team)
            view.onAlert("Teams retrieved from the database", "Success")
        }
    }

    private fun retrieveTeam(responses: Teams) {
        view.onResult(responses)
    }

}