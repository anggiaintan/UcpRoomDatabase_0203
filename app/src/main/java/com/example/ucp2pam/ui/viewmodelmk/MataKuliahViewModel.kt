package com.example.ucp2pam.ui.viewmodelmk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.MataKuliah
import com.example.ucp2pam.repository.RepositoryDosen
import com.example.ucp2pam.repository.RepositoryMataKuliah
import kotlinx.coroutines.launch

class MataKuliahViewModel(private val repositoryMataKuliah: RepositoryMataKuliah, private val repositoryDosen: RepositoryDosen) : ViewModel() {
    var uiStateMataKuliah by mutableStateOf(MataKuliahUIState())
    var dosenList by mutableStateOf(listOf<String>())
    fun getDosenList() {
        viewModelScope.launch {
            repositoryDosen.getAllDosen().collect { dosenEntities ->
                dosenList = dosenEntities.map { it.nama }
            }
        }
    }
    // Memperbarui state berdasarkan input pengguna
    fun updateStateMataKuliah(mataKuliahEvent: MataKuliahEvent) {
        uiStateMataKuliah = uiStateMataKuliah.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }

    // Validasi data input pengguna
    fun validateFields(): Boolean {
        val event = uiStateMataKuliah.mataKuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        uiStateMataKuliah = uiStateMataKuliah.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data ke repository
    fun saveDataMataKuliah() {
        val currentEvent = uiStateMataKuliah.mataKuliahEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.insertMataKuliah(currentEvent.toMataKuliahEntity())
                    uiStateMataKuliah = uiStateMataKuliah.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        mataKuliahEvent = MataKuliahEvent(), // Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    uiStateMataKuliah = uiStateMataKuliah.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiStateMataKuliah =
                uiStateMataKuliah.copy(snackBarMessage = "Input tidak valid. Periksa kembali data Anda.")
        }
    }
    fun resetSnackBarMessageMK() {
        uiStateMataKuliah = uiStateMataKuliah.copy(
            snackBarMessage = null
        )
    }

    // Reset pesan Snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiStateMataKuliah = uiStateMataKuliah.copy(snackBarMessage = null)
    }

    // UI State
    data class MataKuliahUIState(
        val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
        val isEntryValid: FormErrorState = FormErrorState(),
        val snackBarMessage: String? = null,
    )

    // Form Error State
    data class FormErrorState(
        val kode: String? = null,
        val nama: String? = null,
        val sks: String? = null,
        val semester: String? = null,
        val jenis: String? = null,
        val dosenPengampu: String? = null
    ) {
        fun isValid(): Boolean {
            return kode == null && nama == null && sks == null && semester == null &&
                    jenis == null && dosenPengampu == null
        }
    }

    // Event class untuk menyimpan data dari input form
    data class MataKuliahEvent(
        val kode: String = "",
        val nama: String = "",
        val sks: String = "",
        val semester: String = "",
        val jenis: String = "",
        val dosenPengampu: String = ""
    )

    // Konversi input form ke entity MataKuliah
    fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}