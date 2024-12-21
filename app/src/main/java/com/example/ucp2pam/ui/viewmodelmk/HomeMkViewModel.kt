package com.example.ucp2pam.ui.viewmodelmk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.MataKuliah
import com.example.ucp2pam.repository.RepositoryMataKuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

class HomeMkViewModel(
    private val repositoryMK: RepositoryMataKuliah
) : ViewModel() {
    val homeUiState: StateFlow<HomeMatkulUiState> = repositoryMK.getAllMk()
        .filterNotNull()
        .map {
            HomeMatkulUiState(
                listMatkul = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeMatkulUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeMatkulUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeMatkulUiState(
                isLoading = true,
            )
        )
}

data class HomeMatkulUiState(
    val listMatkul: List<MataKuliah> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
