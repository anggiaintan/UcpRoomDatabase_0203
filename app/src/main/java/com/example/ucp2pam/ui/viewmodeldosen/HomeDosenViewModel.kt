package com.example.ucp2pam.ui.viewmodeldosen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Dosen
import com.example.ucp2pam.repository.RepositoryDosen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow

class HomeDosenViewModel(
    private val repositoryDosen: RepositoryDosen
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> = repositoryDosen.getAllDosen()
        .filterNotNull()
        .map { HomeUiState(
            listDosen = it.toList(),
            isLoading = false,
        ) }
        .onStart {
            emit(HomeUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(isLoading = true,)
        )
}

data class HomeUiState(
    val listDosen: List<Dosen> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)