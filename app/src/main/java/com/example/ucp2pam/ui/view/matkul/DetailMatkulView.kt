package com.example.ucp2pam.ui.view.matkul

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2pam.R
import com.example.ucp2pam.data.entity.MataKuliah

@Composable
fun ItemDetailMatkul(
    modifier: Modifier = Modifier,
    matkul: MataKuliah
){
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.black),
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
) {
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
private fun DeleteConfirmationDialogMatkul(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text("Delete Data") },
        text = { Text("Apakah Anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Tidak")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Ya")
            }
        }
    )
}