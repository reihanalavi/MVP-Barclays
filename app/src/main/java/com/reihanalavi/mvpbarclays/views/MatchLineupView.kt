package com.reihanalavi.mvpbarclays.views

import com.reihanalavi.mvpbarclays.models.Pasts
import com.reihanalavi.mvpbarclays.models.Teams

interface MatchLineupView {

    fun showLoading()
    fun hideLoading()
    fun onAlert(message: String, title: String)
    fun onError(error: String)
    fun onResult(data: Pasts?, homeForm: String?, awayForm: String?)

}