package com.reihanalavi.mvpbarclays.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


data class TeamsResponse(
    @SerializedName("teams") var teams: List<Teams>?
)

@Entity
data class Teams (

    @SerializedName("idAPIfootball")
    @ColumnInfo(name = "id_api") var idAPIfootball: String? = null,

    @SerializedName("idTeam")
    @ColumnInfo(name = "team_id") var idTeam: String? = null,

    @SerializedName("strDescriptionEN")
    @ColumnInfo(name = "team_desc") var strDescriptionEN: String? = null,

    @SerializedName("strStadium")
    @ColumnInfo(name = "stadium_name") var strStadium: String? = null,

    @SerializedName("strStadiumDescription")
    @ColumnInfo(name = "stadium_desc") var strStadiumDescription: String? = null,

    @SerializedName("strStadiumLocation")
    @ColumnInfo(name = "stadium_location") var strStadiumLocation: String? = null,

    @SerializedName("strTeam")
    @ColumnInfo(name = "team_name") var strTeam: String? = null,

    @SerializedName("strTeamBadge")
    @ColumnInfo(name = "team_badge") var strTeamBadge: String? = null,

    @SerializedName("strTeamJersey")
    @ColumnInfo(name = "team_jersey") var strTeamJersey: String? = null,

    @SerializedName("strTeamShort")
    @ColumnInfo(name = "team_short") var strTeamShort: String? = null,

    @SerializedName("strStadiumThumb")
    @ColumnInfo(name = "stadium_thumb") var strStadiumThumb: String? = null

) {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

}