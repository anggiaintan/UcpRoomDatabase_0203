package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow


interface RepositoryMataKuliah {
    // Menambahkan data mata kuliah
    suspend fun insertMk(mataKuliah: MataKuliah)

    // Mengambil semua data mata kuliah
    fun getAllMk(): Flow<List<MataKuliah>>

    // Mengambil data mata kuliah berdasarkan kode
    fun getMk(kode: String): Flow<MataKuliah>

    // Menghapus data mata kuliah
    suspend fun deleteMk(mataKuliah: MataKuliah)

    // Memperbarui data mata kuliah
    suspend fun updateMk(mataKuliah: MataKuliah)
}