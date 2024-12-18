package com.example.ucp2pam.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ucp2pam.data.dao.DosenDao
import com.example.ucp2pam.data.dao.MataKuliahDao
import com.example.ucp2pam.data.entity.Dosen
import com.example.ucp2pam.data.entity.MataKuliah

@Database(entities = [Dosen::class, MataKuliah::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dosenDao(): DosenDao
    abstract fun mataKuliahDao(): MataKuliahDao
}