package com.example.ucp2pam.ui.view.matkul

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.R
import com.example.ucp2pam.ui.customwidget.CustomTopAppBar
import com.example.ucp2pam.ui.navigation.AlamatNavigasi
import com.example.ucp2pam.ui.viewmodeldosen.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel
import com.example.ucp2pam.ui.viewmodelmk.toMataKuliahEntity
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.MataKuliahEvent
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel.FormErrorState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiInsertMatkul : AlamatNavigasi {
    override val route: String = "insert_matkul"
}
@Composable
fun InsertMatkulView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiStateMK
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getDosenList() // Ambil data dosen saat layar dimuat
    }

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessageMK()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ) {
            CustomTopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mata Kuliah"
            )

            // Body
            InsertBodyMatkul(
                uiState = uiState,
                dosenList = viewModel.dosenList,
                onValueChange = { updateEvent ->
                    viewModel.updateStateMK(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFieldsMK()) {
                            viewModel.saveDataMK()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate()
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun InsertBodyMatkul(
    modifier: Modifier = Modifier,
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MataKuliahViewModel.MatkulUIState,
    dosenList: List<String>,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatkul(
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            dosenList = dosenList,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9C9F5B),
                contentColor = Color.White
            )
        ) {
            Text("Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMatkul(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    dosenList: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Fields Kode, Nama, SKS, Semester (sama seperti sebelumnya)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kode,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(kode = it))
            },
            label = { Text("Kode Mata Kuliah") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") },
        )
        Text(text = errorState.kode ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.nama,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(nama = it))
            },
            label = { Text("Nama Mata Kuliah") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama Mata Kuliah") },
        )
        Text(text = errorState.nama ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.sks,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(sks = it))
            },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan SKS") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.sks ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.semester,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(semester = it))
            },
            label = { Text("Semester") },
            isError = errorState.semester != null,
            placeholder = { Text("Masukkan Semester") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.semester ?: "", color = Color.Red)


        // Dropdown untuk Jenis Mata Kuliah
        var expandedJenis by remember { mutableStateOf(false) }
        var selectedJenis by remember { mutableStateOf(mataKuliahEvent.jenis) }
        val jenisOptions = listOf("Wajib","Peminatan")

        ExposedDropdownMenuBox(
            expanded = expandedJenis,
            onExpandedChange = { expandedJenis = !expandedJenis }
        ) {
            OutlinedTextField(
                value = selectedJenis,
                onValueChange = { },
                readOnly = true,
                label = { Text("Jenis Mata Kuliah") },
                isError = errorState.jenis != null,
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expandedJenis,
                onDismissRequest = { expandedJenis = false }
            ) {
                jenisOptions.forEach { jenis ->
                    DropdownMenuItem(
                        onClick = {
                            selectedJenis = jenis
                            onValueChange(mataKuliahEvent.copy(jenis = jenis))
                            expandedJenis = false
                        },
                        text = { Text(jenis) }
                    )
                }
            }
        }
        Text(text = errorState.jenis ?: "", color = Color.Red)
    }
    // Dropdown untuk Dosen Pengampu (sama seperti sebelumnya)
    var expandedDosen by remember { mutableStateOf(false) }
    var selectedDosen by remember { mutableStateOf(mataKuliahEvent.dosenPengampu) }

    ExposedDropdownMenuBox(
        expanded = expandedDosen,
        onExpandedChange = { expandedDosen = !expandedDosen }
    ) {
        OutlinedTextField(
            value = selectedDosen,
            onValueChange = { },
            readOnly = true,
            label = { Text("Dosen Pengampu") },
            isError = errorState.dosenPengampu != null,
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        )
        ExposedDropdownMenu(
            expanded = expandedDosen,
            onDismissRequest = { expandedDosen = false }
        ) {
            dosenList.forEach { dosen ->
                DropdownMenuItem(
                    onClick = {
                        selectedDosen = dosen
                        onValueChange(mataKuliahEvent.copy(dosenPengampu = dosen))
                        expandedDosen = false
                    },
                    text = { Text(dosen) }
                )
            }
        }
    }
    Text(text = errorState.dosenPengampu ?: "", color = Color.Red)

}