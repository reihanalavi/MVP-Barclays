package com.reihanalavi.mvpbarclays.views

import com.reihanalavi.mvpbarclays.models.Teams

interface DetailView {

    fun showLoading()
    fun hideLoading()
    fun onAlert(message: String, title: String)
    fun onError(error: String)
    fun onResult(data: List<Teams>?)

}