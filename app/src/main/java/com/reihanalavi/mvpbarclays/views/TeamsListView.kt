package com.reihanalavi.mvpbarclays.views

import android.content.Context
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.models.TeamsResponse

interface TeamsListView {

    fun showLoading()
    fun hideLoading()
    fun onAlert(message: String, title: String)
    fun onError(error: String)
    fun onResult(data: List<Teams>?)

}