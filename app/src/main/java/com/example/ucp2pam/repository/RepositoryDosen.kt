package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {
    // Fungsi untuk menambahkan data dosen
    suspend fun insertDosen(dosen: Dosen)

    // Fungsi untuk membaca semua data dosen
    fun getAllDosen(): Flow <List<Dosen>>

    fun getDosen(nidn: String) : Flow<Dosen>

}