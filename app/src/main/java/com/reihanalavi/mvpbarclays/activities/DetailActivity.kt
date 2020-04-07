package com.reihanalavi.mvpbarclays.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.reihanalavi.mvpbarclays.R
import com.reihanalavi.mvpbarclays.databinding.ActivityDetailBinding
import com.reihanalavi.mvpbarclays.models.Teams
import com.reihanalavi.mvpbarclays.presenters.DetailPresenter
import com.reihanalavi.mvpbarclays.views.DetailView
import com.reihanalavi.mvpbarclays.webservices.ApiRepository
import com.reihanalavi.mvpbarclays.webservices.RetrofitBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import retrofit2.Retrofit

class DetailActivity : AppCompatActivity(), DetailView, AnkoLogger {

    lateinit var retrofit: Retrofit
    lateinit var apiRepository: ApiRepository

    lateinit var presenter: DetailPresenter
    lateinit var dataBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val intent = intent.extras

        val isLocal = intent?.getBoolean("isLocal")
        val id = intent?.getInt("id")

        retrofit = RetrofitBuilder.getRetrofit()
        apiRepository = retrofit.create(ApiRepository::class.java)
        presenter = DetailPresenter(this, apiRepository, application)

        id?.let { presenter.getTeamDetailsFromDatabase(it) }

//        if(isLocal!!) {
//            //it is local
//            id?.let { presenter.getTeamDetailsFromDatabase(it) }
//        } else {
//            //it is from the server
//            val teamsNew = Teams()
//
//            teamsNew.strStadiumThumb = intent.getString("stadiumThumb")
//            teamsNew.strStadium = intent.getString("stadiumName")
//            teamsNew.strStadiumLocation = intent.getString("stadiumLoc")
//            teamsNew.strTeamBadge = intent.getString("teamBadge")
//            teamsNew.strDescriptionEN = intent.getString("teamDesc")
//            teamsNew.strTeamJersey = intent.getString("teamJersey")
//
//            onResult(teamsNew)
//        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun showLoading() {
        toast("Loading")
    }

    override fun hideLoading() {
        toast("Selesai")
    }

    override fun onAlert(message: String, title: String) {
        //
    }

    override fun onError(error: String) {
        Log.d("ERROR DETAIL GAIS", error)
    }

    override fun onResult(data: Teams?) {
        dataBinding.teams = data
    }
}
