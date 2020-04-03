package com.reihanalavi.mvpbarclays.models

import Team
import com.google.gson.annotations.SerializedName

data class TeamsResponse(
    @SerializedName("teams")
    val teams: List<Teams>?
)