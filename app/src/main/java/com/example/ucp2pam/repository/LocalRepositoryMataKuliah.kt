package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.MataKuliahDao
import com.example.ucp2pam.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMataKuliah (
    private val mataKuliahDao: MataKuliahDao
) : RepositoryMataKuliah { // Implementasi interface RepositoryMataKuliah

    // Menambahkan data mata kuliah
    override suspend fun insertMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahDao.insertMataKuliah(mataKuliah)
    }

    // Mengambil semua data mata kuliah
    override fun getAllMataKuliah(): Flow<List<MataKuliah>> {
        return mataKuliahDao.getAllMataKuliah()
    }

    // Mengambil data mata kuliah berdasarkan kode
    override fun getMataKuliah(kode: String): Flow<MataKuliah> {
        return mataKuliahDao.getMataKuliah(kode)
    }

    // Menghapus data mata kuliah
    override suspend fun deleteMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahDao.deleteMataKuliah(mataKuliah)
    }

    // Memperbarui data mata kuliah
    override suspend fun updateMataKuliah(mataKuliah: MataKuliah) {
        mataKuliahDao.updateMataKuliah(mataKuliah)
    }
}