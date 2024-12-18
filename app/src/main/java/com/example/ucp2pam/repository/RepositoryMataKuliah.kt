package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow


interface RepositoryMataKuliah {
    // Menambahkan data mata kuliah
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    // Mengambil semua data mata kuliah
    fun getAllMataKuliah(): Flow<List<MataKuliah>>

    // Mengambil data mata kuliah berdasarkan kode
    fun getMataKuliah(kode: String): Flow<MataKuliah>

    // Menghapus data mata kuliah
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    // Memperbarui data mata kuliah
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)
}