package com.reihanalavi.mvpbarclays.views

import com.reihanalavi.mvpbarclays.models.Pasts

interface PastListDetailView {

    fun showLoading()
    fun hideLoading()
    fun onAlert(message: String, title: String)
    fun onError(error: String)
    fun onResult(data: Pasts?)

}