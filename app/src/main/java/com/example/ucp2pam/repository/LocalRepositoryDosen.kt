package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.DosenDao
import com.example.ucp2pam.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDosen (
    private val dosenDao: DosenDao
) : RepositoryDosen { // Implementasi interface RepositoryDosen

    // Menambahkan data dosen
    override suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }

    // Mengambil semua data dosen
    override fun getAllDosen(): Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

    // Mengambil data dosen berdasarkan nidn
    override fun getDosen(nidn: String): Flow<Dosen> {
        return dosenDao.getDosen(nidn)
    }
}