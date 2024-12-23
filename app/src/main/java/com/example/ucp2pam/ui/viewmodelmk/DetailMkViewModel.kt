package com.example.ucp2pam.ui.viewmodelmk
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.MataKuliah
import com.example.ucp2pam.repository.RepositoryMataKuliah
import com.example.ucp2pam.ui.navigation.DestinasiDetailMatkul
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.MataKuliahEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMataKuliahViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah,
) : ViewModel() {
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiDetailMatkul.KODE])

    val detailUiState: StateFlow<DetailMatkulUiState> = repositoryMataKuliah.getMk(_kode)
        .filterNotNull()
        .map {
            DetailMatkulUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailMatkulUiState(isLoading = true))
            delay(500)
        }
        .catch {
            emit(
                DetailMatkulUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailMatkulUiState(
                isLoading = true,
            ),
        )

    fun deleteMatkul() {
        detailUiState.value.detailUiEvent.toMataKuliahEntity().let {
            viewModelScope.launch {
                repositoryMataKuliah.deleteMk(it)
            }
        }
    }
}

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

// Memindahkan data dari entity ke UI
fun MataKuliah.toDetailUiEvent(): MataKuliahEvent {
    return MataKuliahEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}
// Menyimpan input form ke dalam entity
fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)