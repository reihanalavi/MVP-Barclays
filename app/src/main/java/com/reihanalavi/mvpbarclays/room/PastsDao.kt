package com.reihanalavi.mvpbarclays.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.reihanalavi.mvpbarclays.models.Pasts

@Dao
interface PastsDao {

    @Insert
    suspend fun insertPasts(vararg events: Pasts): List<Long>

    @Query("SELECT * FROM pasts")
    suspend fun getPasts(): List<Pasts>

    @Query("SELECT * FROM pasts WHERE uid = :uid")
    suspend fun getPastByUid(uid: Int): Pasts

    @Query("SELECT * FROM pasts WHERE id_event = :idEvent")
    suspend fun getPastById(idEvent: String): Pasts

    @Query("DELETE FROM pasts")
    suspend fun deletePasts()

    @Query("DELETE FROM pasts WHERE uid = :uid")
    suspend fun deletePastByUid(uid: Int)

}