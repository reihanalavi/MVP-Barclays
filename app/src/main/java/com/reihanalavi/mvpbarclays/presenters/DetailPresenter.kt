package com.reihanalavi.mvpbarclays.presenters

import android.app.Application
import android.util.Log
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.room.BaseViewModel
import com.reihanalavi.mvpbarclays.room.TeamsDatabase
import com.reihanalavi.mvpbarclays.views.DetailView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger

class DetailPresenter(val view: DetailView, var apiRepository: ApiRepository, application: Application): AnkoLogger, BaseViewModel(application) {

    fun getTeamDetailsFromDatabase(idTeam: Int) {
        view.showLoading()

        launch {
            val team = TeamsDatabase(getApplication()).teamsDao().getTeamByUid(idTeam)
            retrieveTeam(team)
//            Log.d("TEAM NAME", team.strTeam.toString())
            view.onAlert("Teams retrieved from the database", "Success")
        }
    }

    private fun retrieveTeam(responses: Teams) {
        view.onResult(responses)
    }

}