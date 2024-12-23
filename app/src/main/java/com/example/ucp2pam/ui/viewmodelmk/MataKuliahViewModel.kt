package com.example.ucp2pam.ui.viewmodelmk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.MataKuliah
import com.example.ucp2pam.repository.RepositoryDosen
import com.example.ucp2pam.repository.RepositoryMataKuliah
import kotlinx.coroutines.launch

class MataKuliahViewModel(private val repositoryMataKuliah: RepositoryMataKuliah, private val repositoryDosen: RepositoryDosen) : ViewModel() {
    var uiStateMK by mutableStateOf(MatkulUIState())

    var dosenList by mutableStateOf(listOf<String>())

    // Ambil daftar dosen dari repository
    fun getDosenList() {
        viewModelScope.launch {
            repositoryDosen.getAllDosen().collect { dosenEntities ->
                dosenList = dosenEntities.map { it.nama }
            }
        }
    }

    // Memperbarui state berdasarkan input pengguna
    fun updateStateMK(mataKuliahEvent: MataKuliahEvent) {
        uiStateMK = uiStateMK.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }

    // Validasi data input pengguna
    fun validateFieldsMK(): Boolean {
        val event = uiStateMK.mataKuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )

        uiStateMK = uiStateMK.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data ke repository
    fun saveDataMK() {
        val currentEvent = uiStateMK.mataKuliahEvent
        if (validateFieldsMK()) {
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.insertMk(currentEvent.toMataKuliahEntity())
                    uiStateMK = uiStateMK.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        mataKuliahEvent = MataKuliahEvent(), // Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    uiStateMK = uiStateMK.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiStateMK = uiStateMK.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data Anda."
            )
        }
    }

    // Reset pesan Snackbar setelah ditampilkan
    fun resetSnackBarMessageMK() {
        uiStateMK = uiStateMK.copy(
            snackBarMessage = null
        )
    }
    //membuat data class untuk membungkus data class lainnya
    data class MatkulUIState(
        val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
        val isEntryValid: FormErrorState = FormErrorState(),
        val snackBarMessage: String? = null,
    )

    data class FormErrorState(
        val kode: String? = null,
        val nama: String? = null,
        val sks: String? = null,
        val semester: String? = null,
        val jenis: String? = null,
        val dosenPengampu: String? = null,
    ) {
        fun isValid(): Boolean {
            return kode == null && nama == null && sks == null &&
                    semester == null && jenis == null && dosenPengampu == null
        }
    }
    // Data class untuk menyimpan data input form
    data class MataKuliahEvent(
        val kode: String = "",
        val nama: String = "",
        val sks: String = "",
        val semester: String = "",
        val jenis: String = "",
        val dosenPengampu: String = ""
    )

    // Menyimpan input form ke dalam entity
    fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}