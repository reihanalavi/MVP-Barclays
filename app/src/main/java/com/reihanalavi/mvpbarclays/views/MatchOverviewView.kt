package com.reihanalavi.mvpbarclays.views

import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.models.Teams

interface MatchOverviewView {

    fun showLoading()
    fun hideLoading()
    fun onAlert(message: String, title: String)
    fun onError(error: String)
    fun onResultOverview(data: Pasts?)
    fun onResultTeam(data: Teams?, side: String)

}