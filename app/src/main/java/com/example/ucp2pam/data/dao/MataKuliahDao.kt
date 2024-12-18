package com.example.ucp2pam.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2pam.data.entity.MataKuliah

@Dao
interface MataKuliahDao {
    @Insert
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    @Query("SELECT * FROM MataKuliah")
    suspend fun getAllMataKuliah(): List<MataKuliah>

    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)

    @Delete
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    @Query("SELECT * FROM MataKuliah WHERE kode = :kode")
    suspend fun getDetailMataKuliah(kode: Int): MataKuliah
}