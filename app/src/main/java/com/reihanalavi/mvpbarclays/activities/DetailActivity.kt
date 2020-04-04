package com.reihanalavi.mvpbarclays.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.reihanalavi.mvpbarclays.R
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent.extras

        retrofit = RetrofitBuilder.getRetrofit()
        apiRepository = retrofit.create(ApiRepository::class.java)
        presenter = DetailPresenter(this, apiRepository)

        intent?.getString("id")?.let { presenter.getTeamDetails(it) }
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

    override fun onResult(data: List<Teams>?) {
        val detail = data?.get(0)

        Picasso.get().load(detail?.strStadiumThumb).fit().into(imageView_details_stadium)
        Picasso.get().load(detail?.strTeamBadge).into(imageView_details_badge)

        textView_detail_stadium.text = detail?.strStadium
        textView_detail_location.text = detail?.strStadiumLocation
        textView_details_desc.text = detail?.strDescriptionEN

    }
}
