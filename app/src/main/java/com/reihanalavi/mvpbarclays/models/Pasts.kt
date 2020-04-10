package com.reihanalavi.mvpbarclays.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PastsResponse(
    @SerializedName("events") var events: List<Pasts>
)

@Entity
data class Pasts(
    @SerializedName("idEvent") @ColumnInfo(name = "id_event") var idEvent: String? = null,
    @SerializedName("strEvent") @ColumnInfo(name = "str_event") var strEvent: String? = null,

    @SerializedName("strHomeTeam") @ColumnInfo(name = "str_home_team") var strHomeTeam: String? = null,
    @SerializedName("intHomeScore") @ColumnInfo(name = "int_home_score") var intHomeScore: String? = null,
    @SerializedName("strHomeGoalDetails") @ColumnInfo(name = "str_home_goal_details") var strHomeGoalDetails: String? = null,
    @SerializedName("strHomeRedCards") @ColumnInfo(name = "str_home_red_cards") var strHomeRedCards: String? = null,
    @SerializedName("strHomeYellowCards") @ColumnInfo(name = "str_home_yellow_cards") var strHomeYellowCards: String? = null,
    @SerializedName("strHomeLineupGoalkeeper") @ColumnInfo(name = "str_home_lineup_goalkeeper") var strHomeLineupGoalkeeper: String? = null,
    @SerializedName("strHomeLineupDefense") @ColumnInfo(name = "str_home_lineup_defense") var strHomeLineupDefense: String? = null,
    @SerializedName("strHomeLineupMidfield") @ColumnInfo(name = "str_home_lineup_midfield") var strHomeLineupMidfield: String? = null,
    @SerializedName("strHomeLineupForward") @ColumnInfo(name = "str_home_lineup_forward") var strHomeLineupForward: String? = null,
    @SerializedName("strHomeLineupSubstitutes") @ColumnInfo(name = "str_home_lineup_substitutes") var strHomeLineupSubstitutes: String? = null,
    @SerializedName("strHomeFormation") @ColumnInfo(name = "str_home_formation") var strHomeFormation: String? = null,
    @SerializedName("intHomeShots") @ColumnInfo(name = "int_home_shots") var intHomeShots: String? = null,

    @SerializedName("strAwayTeam") @ColumnInfo(name = "str_away_team") var strAwayTeam: String? = null,
    @SerializedName("intAwayScore") @ColumnInfo(name = "int_away_score") var intAwayScore: String? = null,
    @SerializedName("strAwayGoalDetails") @ColumnInfo(name = "str_away_goal_details") var strAwayGoalDetails: String? = null,
    @SerializedName("strAwayRedCards") @ColumnInfo(name = "str_away_red_cards") var strAwayRedCards: String? = null,
    @SerializedName("strAwayYellowCards") @ColumnInfo(name = "str_away_yellow_cards") var strAwayYellowCards: String? = null,
    @SerializedName("strAwayLineupGoalkeeper") @ColumnInfo(name = "str_away_lineup_goalkeeper") var strAwayLineupGoalkeeper: String? = null,
    @SerializedName("strAwayLineupDefense") @ColumnInfo(name = "str_away_lineup_defense") var strAwayLineupDefense: String? = null,
    @SerializedName("strAwayLineupMidfield") @ColumnInfo(name = "str_away_lineup_midfield") var strAwayLineupMidfield: String? = null,
    @SerializedName("strAwayLineupForward") @ColumnInfo(name = "str_away_lineup_forward") var strAwayLineupForward: String? = null,
    @SerializedName("strAwayLineupSubstitutes") @ColumnInfo(name = "str_away_lineup_substitutes") var strAwayLineupSubstitutes: String? = null,
    @SerializedName("strAwayFormation") @ColumnInfo(name = "str_away_formation") var strAwayFormation: String? = null,
    @SerializedName("intAwayShots") @ColumnInfo(name = "int_away_shots") var intAwayShots: String? = null,

    @SerializedName("dateEvent") @ColumnInfo(name = "date_event") var dateEvent: String? = null,
    @SerializedName("idHomeTeam") @ColumnInfo(name = "id_home_team") var idHomeTeam: String? = null,
    @SerializedName("idAwayTeam") @ColumnInfo(name = "id_away_team") var idAwayTeam: String? = null,
    @SerializedName("strThumb") @ColumnInfo(name = "str_thumb") var strThumb: String? = null

) {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

}