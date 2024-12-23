package com.example.ucp2pam.ui.view.matkul

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.R
import com.example.ucp2pam.data.entity.MataKuliah
import com.example.ucp2pam.ui.customwidget.CustomTopAppBar
import com.example.ucp2pam.ui.viewmodeldosen.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodelmk.DetailMataKuliahViewModel
import com.example.ucp2pam.ui.viewmodelmk.DetailMatkulUiState
import com.example.ucp2pam.ui.viewmodelmk.toMataKuliahEntity

@Composable
fun DetailMatkulView(
    modifier: Modifier = Modifier,
    viewModel: DetailMataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { },
    onDeleteClick: () -> Unit = { },
    onEditClick: (String) -> Unit = {}
){
    Scaffold (
        topBar = {
            CustomTopAppBar(
                judul = "Detail Mata Kuliah",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailUiState.value.detailUiEvent.kode)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFF9C9F5B)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    tint = colorResource(id = R.color.white),
                    contentDescription = "Edit Mata Kuliah",
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()
        BodyDetailMatkul(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteMatkul()
                onDeleteClick()
            }
        )
    }
}


@Composable
fun BodyDetailMatkul(
    modifier: Modifier = Modifier,
    detailUiState: DetailMatkulUiState = DetailMatkulUiState(),
    onDeleteClick: () -> Unit = {}
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMatkul(
                    matkul = detailUiState.detailUiEvent.toMataKuliahEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF9C9F5B),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                    )
                }

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialogMK(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        else -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun ItemDetailMatkul(
    modifier: Modifier = Modifier,
    matkul: MataKuliah
){
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF9C9F5B),
            contentColor = colorResource(id = R.color.white)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMatkul(judul = "Kode", isinya = matkul.kode)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Nama", isinya = matkul.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "SKS", isinya = matkul.sks)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Semester", isinya = matkul.semester)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Jenis", isinya = matkul.jenis)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMatkul(judul = "Dosen Pengampu", isinya = matkul.dosenPengampu)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}


@Composable
fun ComponentDetailMatkul(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialogMK(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}
