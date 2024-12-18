package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.Dosen

interface RepositoryDosen {
    // Fungsi untuk menambahkan data dosen
    suspend fun insertDosen(dosen: Dosen)

    // Fungsi untuk membaca semua data dosen
    fun getAllDosen(): List<Dosen>
}