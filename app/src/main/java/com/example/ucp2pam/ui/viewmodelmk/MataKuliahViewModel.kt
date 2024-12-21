package com.example.ucp2pam.ui.viewmodelmk

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.MataKuliah
import com.example.ucp2pam.repository.RepositoryDosen
import com.example.ucp2pam.repository.RepositoryMataKuliah
import kotlinx.coroutines.launch

class MataKuliahViewModel(private val repositoryMataKuliah: RepositoryMataKuliah, private val repositoryDosen: RepositoryDosen) : ViewModel() {
    var uiState by mutableStateOf(MataKuliahUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState(mataKuliahEvent: MataKuliahEvent) {
        uiState = uiState.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }

    // Validasi data input pengguna
    private fun validateFields(): Boolean {
        val event = uiState.mataKuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks > 0) null else "SKS harus lebih dari 0",
            semester = if (event.semester > 0) null else "Semester harus lebih dari 0",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.mataKuliahEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.insertMataKuliah(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        mataKuliahEvent = MataKuliahEvent(), // Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState =
                uiState.copy(snackBarMessage = "Input tidak valid. Periksa kembali data Anda.")
        }
    }

    // Membaca semua data dari repository
    fun loadAllData() {
        viewModelScope.launch {
            try {
                val mataKuliahList = repositoryMataKuliah.getAllMataKuliah()
                uiState = uiState.copy(
                    mataKuliahList = mataKuliahList
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    snackBarMessage = "Gagal memuat data"
                )
            }
        }
    }

    // Menghapus data
    fun deleteData(mataKuliah: MataKuliah) {
        viewModelScope.launch {
            try {
                repositoryMataKuliah.deleteMataKuliah(mataKuliah)
                uiState = uiState.copy(snackBarMessage = "Data berhasil dihapus")
                loadAllData() // Refresh data
            } catch (e: Exception) {
                uiState = uiState.copy(snackBarMessage = "Data gagal dihapus")
            }
        }
    }

    // Mengupdate data
    fun updateData() {
        val currentEvent = uiState.mataKuliahEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMataKuliah.updateMataKuliah(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        mataKuliahEvent = MataKuliahEvent(), // Reset input form
                        isEntryValid = FormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            uiState = uiState.copy(snackBarMessage = "Input tidak valid. Periksa kembali data Anda.")
        }
    }

    // Reset pesan Snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }

    // UI State
    data class MataKuliahUIState(
        val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
        val isEntryValid: FormErrorState = FormErrorState(),
        val snackBarMessage: String? = null,
        val mataKuliahList: List<MataKuliah> = emptyList()
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
        val semester: Int = 0,
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