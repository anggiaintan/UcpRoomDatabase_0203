package com.example.ucp2pam.ui.view.dosen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.R
import com.example.ucp2pam.data.entity.Dosen
import com.example.ucp2pam.ui.costumwidget.CustomTopAppBar
import com.example.ucp2pam.ui.viewmodeldosen.HomeDosenViewModel
import com.example.ucp2pam.ui.viewmodeldosen.HomeUiState
import com.example.ucp2pam.ui.viewmodeldosen.PenyediaViewModel
import java.lang.reflect.Modifier

@Composable
fun HomeDosenView(
    viewModel: HomeDosenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDosen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold (
        topBar = {
            TopAppBar(title = "Daftar Dosen", backgroundColor = colorResource(id = R.color.white))
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDosen,
                shape = CircleShape,
                modifier = Modifier.padding(16.dp),
                containerColor = colorResource(id = R.color.white)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = Color.Gray,
                    contentDescription = "Tambah Dosen"
                )
            }
        }
    ) {
            innerPadding ->
        val dosenUiState by viewModel.homeUiState.collectAsState()
        BodyHomeDosenView(
            dosenUiState = dosenUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeDosenView(
    dosenUiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.teal_700))
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Universitas Muhammadiyah Yogyakarta",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    )
}

@Composable
fun LoadingIndicator() {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = colorResource(id = R.color.white))
    }
}

@Composable
fun ErrorMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Terjadi kesalahan. Silakan coba lagi.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Red
        )
    }
}

@Composable
fun EmptyMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Tidak ada data dosen saat ini.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Composable
fun ListDosen(
    listDosen: List<Dosen>,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(items = listDosen) { dosen ->
            ExpandableCardDosen(dosen = dosen)
        }
    }
}