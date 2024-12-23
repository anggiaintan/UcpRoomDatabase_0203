package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.MataKuliahDao
import com.example.ucp2pam.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMataKuliah (
    private val mataKuliahDao: MataKuliahDao
) : RepositoryMataKuliah { // Implementasi interface RepositoryMataKuliah

    // Menambahkan data mata kuliah
    override suspend fun insertMk(mataKuliah: MataKuliah) {
        mataKuliahDao.insertMatkul(mataKuliah)
    }

    // Mengambil semua data mata kuliah
    override fun getAllMk(): Flow<List<MataKuliah>> {
        return mataKuliahDao.getAllMatkul()
    }

    // Mengambil data mata kuliah berdasarkan kode
    override fun getMk(kode: String): Flow<MataKuliah> {
        return mataKuliahDao.getMatkul(kode)
    }

    // Menghapus data mata kuliah
    override suspend fun deleteMk(mataKuliah: MataKuliah) {
        mataKuliahDao.deleteMatkul(mataKuliah)
    }

    // Memperbarui data mata kuliah
    override suspend fun updateMk(mataKuliah: MataKuliah) {
        mataKuliahDao.updateMatkul(mataKuliah)
    }
}