package com.reihanalavi.mvpbarclays.models

import com.google.gson.annotations.SerializedName

data class Teams (

    @SerializedName("strTeamShort") val teamShort: String?,
    @SerializedName("strTeam") val teamLong: String?,
    @SerializedName("strStadium") val teamStadium: String?

)