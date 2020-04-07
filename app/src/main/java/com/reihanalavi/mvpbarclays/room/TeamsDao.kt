package com.reihanalavi.mvpbarclays.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.reihanalavi.mvpbarclays.models.Teams
import io.reactivex.Completable

@Dao
interface TeamsDao {

    @Insert
    suspend fun insertTeams(vararg teams: Teams): List<Long>

    @Query("SELECT * FROM teams")
    suspend fun getTeams(): List<Teams>

    @Query("SELECT * FROM teams WHERE uid = :idTeam")
    suspend fun getTeamByUid(idTeam: Int): Teams

    @Query("SELECT * FROM teams WHERE team_id = :idTeam")
    suspend fun getTeamById(idTeam: String): Teams

    @Query("DELETE FROM teams")
    suspend fun deleteTeams()

    @Query("DELETE FROM teams WHERE uid = :uid")
    suspend fun deleteTeam(uid: Int)

}