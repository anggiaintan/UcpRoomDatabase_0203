package com.example.ucp2pam.ui.viewmodeldosen
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Dosen
import com.example.ucp2pam.repository.RepositoryDosen
import com.example.ucp2pam.ui.viewmodeldosen.DosenViewModel.DosenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailDosenViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryDosen: RepositoryDosen,
) : ViewModel() {
    private val _nidn: String = checkNotNull(savedStateHandle[DestinasiDetailDosen.NIDN])

    val detailUiState: StateFlow<DetailDosenUiState> = repositoryDosen.getDosen(_nidn)
        .filterNotNull()
        .map {
            DetailDosenUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailDosenUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailDosenUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailDosenUiState(isLoading = true)
        )

    fun deleteDosen() {
        detailUiState.value.detailUiEvent.toDosenEntity().let {
            viewModelScope.launch {
                repositoryDosen.deleteDosen(it)
            }
        }
    }
}

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
// Memindahkan data dari entity ke UI
fun Dosen.toDetailUiEvent(): DosenEvent {
    return DosenEvent(
        nidn = nidn,
        nama = nama,
        jenisKelamin = jenisKelamin
    )
}


fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)