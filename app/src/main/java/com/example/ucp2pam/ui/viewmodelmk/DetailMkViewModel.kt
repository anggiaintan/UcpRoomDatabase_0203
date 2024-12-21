package com.example.ucp2pam.ui.viewmodelmk
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.MataKuliahEvent

data class DetailMatkulUiState(
    val detailUiEvent: MataKuliahEvent = MataKuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MataKuliahEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MataKuliahEvent()
}