package com.example.ucp2pam.ui.view.matkul

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMatkul(
    mataKuliahEvent: MataKuliahViewModel.MataKuliahEvent = MataKuliahViewModel.MataKuliahEvent(),
    onValueChange: (MataKuliahViewModel.MataKuliahEvent) -> Unit,
    errorState: MataKuliahViewModel.FormErrorState = MataKuliahViewModel.FormErrorState(),
    dosenList: List<String>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
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

        var expandedJenis by remember { mutableStateOf(false) }
        var selectedJenis by remember { mutableStateOf(mataKuliahEvent.jenis) }

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
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expandedJenis,
                onDismissRequest = { expandedJenis = false }
            ) {
                val jenisList = listOf("Wajib", "Peminatan") // Pilihan untuk jenis
                jenisList.forEach { jenis ->
                    androidx.compose.material3.DropdownMenuItem(
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


        var expanded by remember { mutableStateOf(false) }
        var selectedDosen by remember { mutableStateOf(matkulEvent.dosenPengampu) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedDosen,
                onValueChange = { },
                readOnly = true,
                label = { Text("Dosen Pengampu") },
                isError = errorState.dosenPengampu != null,
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                trailingIcon = {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                dosenList.forEach { dosen ->
                    androidx.compose.material3.DropdownMenuItem(
                        onClick = {
                            selectedDosen = dosen
                            onValueChange(mataKuliahEvent.copy(dosenPengampu = dosen))
                            expanded = false
                        },
                        text = { Text(dosen) }
                    )
                }
            }
        }
        Text(text = errorState.dosenPengampu ?: "", color = Color.Red)
    }
}