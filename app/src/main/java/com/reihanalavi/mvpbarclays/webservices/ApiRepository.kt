package com.reihanalavi.mvpbarclays.webservices

import com.reihanalavi.mvpbarclays.models.Teams
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRepository {

    @GET("v1/json/1/search_all_teams.php")
    fun getTeams(@Query ("l") league: String): Observable<ArrayList<Teams>>

}