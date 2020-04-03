package com.reihanalavi.mvpbarclays.views

import android.content.Context
import com.reihanalavi.mvpbarclays.models.Teams

interface MainView {

    fun showLoading()
    fun hideLoading()
    fun onAlert(message: String, title: String)
    fun onError(error: String)
    fun onResult(data: ArrayList<Teams>)

}