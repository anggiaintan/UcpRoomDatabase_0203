package com.example.ucp2pam.ui.viewmodelmk

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.MataKuliah
import com.example.ucp2pam.repository.RepositoryDosen
import com.example.ucp2pam.repository.RepositoryMataKuliah
import com.example.ucp2pam.ui.navigation.DestinasiUpdateMatkul
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.MataKuliahUIState
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.MataKuliahEvent
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.FormErrorState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMkViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    var updateUIState by mutableStateOf(MataKuliahUIState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMatkul.KODE])

    var dosenList by mutableStateOf(listOf<String>())

    // Ambil daftar dosen dari repository
    fun getDosenList() {
        viewModelScope.launch {
            repositoryDosen.getAllDosen().collect { dosenEntities ->
                dosenList = dosenEntities.map { it.nama }
            }
        }
    }
    init {
        viewModelScope.launch {
            updateUIState = repositoryMataKuliah.getMataKuliah(_kode)
                .filterNotNull()
                .first()
                .toUIStateMatkul()
            getDosenList()
        }
    }

    fun MataKuliah.toUIStateMatkul(): MataKuliahUIState {
        return MataKuliahUIState(
            mataKuliahEvent = MataKuliahEvent(
                kode = this.kode,
                nama = this.nama,
                sks = this.sks,
                semester = this.semester,
                jenis = this.jenis,
                dosenPengampu = this.dosenPengampu
            )
        )
    }

    fun updateState (mataKuliahEvent: MataKuliahEvent) {
        updateUIState = updateUIState.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.mataKuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()


    }

    fun updateDataMK() {
        val currentEvent = updateUIState.mataKuliahEvent
        if (validateFields()) {
    viewModelScope.launch {
        try {
            repositoryMataKuliah.updateMataKuliah(currentEvent.toMataKuliahEntity())
            updateUIState = updateUIState.copy(
    snackBarMessage = "Data berhasil diupdate",
    mataKuliahEvent = MataKuliahEvent(), // Reset input form
    isEntryValid = FormErrorState() // Reset error state
)
} catch (e: Exception) {
    updateUIState = updateUIState.copy(
    snackBarMessage = "Data gagal diupdate"
)
}
}
        }
else {
    updateUIState = updateUIState.copy(snackBarMessage = "Data gagal diupdate")

}
    }
fun resetSnackBarMessage() {
    updateUIState = updateUIState.copy(snackBarMessage = null)

}}