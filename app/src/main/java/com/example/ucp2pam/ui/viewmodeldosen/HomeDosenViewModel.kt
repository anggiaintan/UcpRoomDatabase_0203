package com.example.ucp2pam.ui.viewmodeldosen

import com.example.ucp2pam.data.entity.Dosen

data class HomeUiState(
    val listDosen: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)