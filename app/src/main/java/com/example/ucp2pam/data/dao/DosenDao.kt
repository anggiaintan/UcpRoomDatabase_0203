package com.example.ucp2pam.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2pam.data.entity.Dosen

@Dao
interface DosenDao {
    @Insert
    suspend fun insertDosen(dosen: Dosen)

    @Query("SELECT * FROM Dosen")
    suspend fun getAllDosen(): List<Dosen>
}