package com.reihanalavi.mvpbarclays.models

import com.google.gson.annotations.SerializedName

data class TeamsResponse(
    @SerializedName("teams") var teams: List<Teams>?
)