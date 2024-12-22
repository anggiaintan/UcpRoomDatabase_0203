package com.example.ucp2pam.ui.viewmodelmk

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.MataKuliah
import com.example.ucp2pam.repository.RepositoryDosen
import com.example.ucp2pam.repository.RepositoryMataKuliah
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.MataKuliahUIState
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.MataKuliahEvent
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