package com.reihanalavi.mvpbarclays.presenters

import android.app.Application
import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.room.BaseViewModel
import com.reihanalavi.mvpbarclays.room.TeamsDatabase
import com.reihanalavi.mvpbarclays.views.PastListDetailView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger

class PastsListDetailPresenter(val view: PastListDetailView, var apiRepository: ApiRepository, application: Application): AnkoLogger, BaseViewModel(application) {

    fun getPastDetailsFromDatabase(pastUid: Int) {
        view.showLoading()

        launch {
            val past = TeamsDatabase(getApplication()).pastsDao().getPastByUid(pastUid)
            retrievePast(past)
            view.onAlert("Past details retrieved from the database", "Success")
        }
    }

    private fun retrievePast(responses: Pasts) {
        view.onResult(responses)
    }

}