package com.example.ucp2pam.ui.view.dosen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.R
import com.example.ucp2pam.data.entity.Dosen
import com.example.ucp2pam.ui.viewmodeldosen.HomeDosenViewModel
import com.example.ucp2pam.ui.viewmodeldosen.HomeUiState
import com.example.ucp2pam.ui.viewmodeldosen.PenyediaViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun HomeDosenView(
    viewModel: HomeDosenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDosen: () -> Unit = { }, // Fungsi untuk menambahkan dosen baru
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDosen, // Panggil fungsi untuk menambahkan dosen baru
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp),
                containerColor = Color(0xFF9C9F5B)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = colorResource(id = R.color.white),
                    contentDescription = "Tambah Dosen"
                )
            }
        }
    ) { innerPadding ->
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
    Column(modifier = modifier
        .fillMaxSize()
        .background(color = Color(0xFF8B4513))) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(110.dp),
                painter = painterResource(id = R.drawable.umyhd),
                contentDescription = "Logo UMY"
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = "Universitas Muhammadiyah Yogyakarta",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Justify
                )
                Text(
                    text = "Teknologi Informasi",
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(
                        topEnd = 18.dp,
                        topStart = 18.dp
                    )
                )
        ){
            when {
                dosenUiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                dosenUiState.isError -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Terjadi kesalahan.",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                dosenUiState.listDosen.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tidak ada data dosen.",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                else -> {
                    ListDosen(
                        listDosen = dosenUiState.listDosen,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}


@Composable
fun ListDosen(
    listDosen: List<Dosen>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listDosen,
            itemContent = { dosen ->
                ExpandableCardDosen(dosen = dosen)
            }
        )
    }
}

@Composable
fun ExpandableCardDosen(
    dosen: Dosen,
    modifier: Modifier = Modifier
) {
    val cardBackgroundColor = Color(0xFF9C9F5B)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = cardColors(
            containerColor = cardBackgroundColor
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person, // Ikon untuk nama
                    contentDescription = "Nama",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = dosen.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange, // Ikon untuk NIDN
                    contentDescription = "NIDN",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "NIDN: ${dosen.nidn}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Face, // Ikon untuk jenis kelamin
                    contentDescription = "Jenis Kelamin",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Jenis Kelamin: ${dosen.jenisKelamin}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    }
}
