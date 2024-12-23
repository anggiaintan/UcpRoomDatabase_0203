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
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.MataKuliahEvent
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.MatkulUIState
import com.example.ucp2pam.ui.viewmodelmk.toMataKuliahEntity
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.FormErrorState
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMatkulViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah,
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {

    var updateUIState by mutableStateOf(MatkulUIState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMatkul.KODE])

    var dosenList by mutableStateOf(listOf<String>())

    fun getDosenList() {
        viewModelScope.launch {
            repositoryDosen.getAllDosen().collect { dosenEntities ->
                dosenList = dosenEntities.map { it.nama }
            }
        }
    }

    init {
        viewModelScope.launch {
            updateUIState = repositoryMataKuliah.getMk(_kode)
                .filterNotNull()
                .first()
                .toUIStateMatkul()
            getDosenList()
        }
    }

    fun MataKuliah.toUIStateMatkul(): MatkulUIState {
        return MatkulUIState(
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

    //untuk memperbarui state UI dengan data baru yang diterima dalam bentuk MataKuliahEvent
    fun updateState(mataKuliahEvent: MataKuliahEvent) {
        updateUIState = updateUIState.copy(
            mataKuliahEvent = mataKuliahEvent,
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
                    repositoryMataKuliah.updateMk(currentEvent.toMataKuliahEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data Berhasil Diupdate",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}