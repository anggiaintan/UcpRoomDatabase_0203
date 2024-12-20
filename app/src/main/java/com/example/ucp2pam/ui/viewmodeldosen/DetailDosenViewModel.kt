package com.example.ucp2pam.ui.viewmodeldosen
import com.example.ucp2pam.ui.viewmodeldosen.DosenViewModel.DosenEvent

data class DetailDosenUiState(
    val detailUiEvent: DosenEvent = DosenEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == DosenEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != DosenEvent()
}