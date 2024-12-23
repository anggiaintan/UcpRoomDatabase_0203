package com.example.ucp2pam.ui.view.dosen

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
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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

@Composable
fun HomeDosenView(
    viewModel: HomeDosenViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDosen: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold (
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
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(110.dp),
                painter = painterResource(id = R.drawable.umyhd),
                contentDescription = "Logo UMY"
            )

            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Universitas Muhammadiyah Yogyakarta",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Teknologi Informasi",
                    color = Color.White, fontWeight = FontWeight.Light
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(
            color = colorResource(id = R.color.teal_700),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
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
                        text = "Terjadi kesalahan. Silakan coba lagi.",
                        fontSize = 16.sp,
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
                        text = "Tidak ada data dosen saat ini.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            else -> {
                ListDosen(listDosen = dosenUiState.listDosen, modifier = Modifier.fillMaxSize())
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
    LazyColumn (
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(items = listDosen) { dosen ->
            ExpandableCardDosen(dosen = dosen)
        }
    }
}


@Composable
fun ExpandableCardDosen(
    dosen: Dosen,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        onClick = { isExpanded = !isExpanded },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dosen.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f),
                    color = colorResource(id = R.color.black))
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = colorResource(id = R.color.white)
                )
            }
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    Text(
                        text = "NIDN: ${dosen.nidn}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Jenis Kelamin: ${dosen.jenisKelamin}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}