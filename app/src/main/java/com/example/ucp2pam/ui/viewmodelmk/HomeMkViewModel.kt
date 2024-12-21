package com.example.ucp2pam.ui.viewmodelmk

import com.example.ucp2pam.data.entity.MataKuliah

data class HomeMatkulUiState(
    val listMatkul: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
