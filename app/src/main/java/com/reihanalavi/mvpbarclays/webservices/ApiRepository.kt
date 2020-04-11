package com.reihanalavi.mvpbarclays.webservices

import com.reihanalavi.mvpbarclays.models.PastsResponse
import com.reihanalavi.mvpbarclays.models.TeamsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRepository {

    //TEAMS
    @GET("api/v1/json/1/search_all_teams.php")
    fun getTeams(@Query ("l") league: String): Observable<TeamsResponse>

    //PAST EVENTS (18/19)
    @GET("api/v1/json/1/eventsround.php")
    fun getPasts(@Query ("id") idEvent: String,
                 @Query ("r") round: String,
                 @Query ("s") season: String) : Observable<PastsResponse>

    @GET("api/v1/json/1/lookupevent.php")
    fun getPastDetails(@Query ("id") idEvent: String) : Observable<PastsResponse>

}