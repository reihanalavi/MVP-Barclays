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

    @Query("SELECT * FROM teams WHERE uid = :uid")
    suspend fun getTeam(uid: Int): Teams

    @Query("DELETE FROM teams")
    suspend fun deleteTeams()

    @Query("DELETE FROM teams WHERE uid = :uid")
    suspend fun deleteTeam(uid: Int)

}