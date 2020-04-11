package com.reihanalavi.mvpbarclays.presenters

import android.app.Application
import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.room.BaseViewModel
import com.reihanalavi.mvpbarclays.room.TeamsDatabase
import com.reihanalavi.mvpbarclays.views.MatchLineupView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger

class MatchLineupPresenter(val view: MatchLineupView, val apiRepository: ApiRepository, application: Application): AnkoLogger, BaseViewModel(application) {


    fun getPastDetailsFromDatabase(pastUid: Int?) {
        view.showLoading()

        launch {
            val past = TeamsDatabase(getApplication()).pastsDao().getPastByUid(pastUid!!)
            retrievePast(past)
            view.onAlert("Past details retrieved from the database", "Success")
        }
    }

    private fun retrievePast(responses: Pasts) {

        val homeDef = (responses.strHomeLineupDefense?.split("; ")?.size?.minus(1) ).toString()
        val homeMid = (responses.strHomeLineupMidfield?.split("; ")?.size?.minus(1) ).toString()
        val homeFor = (responses.strHomeLineupForward?.split("; ")?.size?.minus(1) ).toString()

        val awayDef = (responses.strAwayLineupDefense?.split("; ")?.size?.minus(1)).toString()
        val awayMid = (responses.strAwayLineupMidfield?.split("; ")?.size?.minus(1)).toString()
        val awayFor = (responses.strAwayLineupForward?.split("; ")?.size?.minus(1)).toString()

        val homeFormation = "$homeDef - $homeMid - $homeFor"
        val awayFormation = "$awayDef - $awayMid - $awayFor"

        view.onResult(responses, homeFormation, awayFormation)
    }

}