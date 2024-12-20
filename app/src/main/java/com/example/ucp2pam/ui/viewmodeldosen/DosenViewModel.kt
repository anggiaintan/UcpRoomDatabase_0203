package com.example.ucp2pam.ui.viewmodeldosen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Dosen
import com.example.ucp2pam.repository.RepositoryDosen
import kotlinx.coroutines.launch

class DosenViewModel(private val repositoryDosen: RepositoryDosen) : ViewModel() {
    var uiState by mutableStateOf(DosenUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState(dosenEvent: DosenEvent) {
        uiState = uiState.copy(
            dosenEvent = dosenEvent
        )
    }

    // Validasi data input pengguna
    private fun validateFields(): Boolean {
        val event = uiState.dosenEvent
        val errorState = FormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.dosenEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryDosen.insertDosen(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        dosenEvent = DosenEvent(), // Reset input form
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

    // Reset pesan Snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }

    // UI State
    data class DosenUIState(
        val dosenEvent: DosenEvent = DosenEvent(),
        val isEntryValid: FormErrorState = FormErrorState(),
        val snackBarMessage: String? = null,
    )

    // Form Error State
    data class FormErrorState(
        val nidn: String? = null,
        val nama: String? = null,
        val jenisKelamin: String? = null
    ) {
        fun isValid(): Boolean {
            return nidn == null && nama == null && jenisKelamin == null
        }
    }

    // Event class untuk menyimpan data dari input form
    data class DosenEvent(
        val nidn: String = "",
        val nama: String = "",
        val jenisKelamin: String = ""
    )

    // Konversi input form ke entity Dosen
    fun DosenEvent.toDosenEntity(): Dosen = Dosen(
        nidn = nidn,
        nama = nama,
        jenisKelamin = jenisKelamin
    )
}