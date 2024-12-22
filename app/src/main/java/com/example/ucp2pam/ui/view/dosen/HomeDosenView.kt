package com.example.ucp2pam.ui.view.dosen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.R
import com.example.ucp2pam.ui.costumwidget.CustomTopAppBar
import com.example.ucp2pam.ui.viewmodeldosen.HomeDosenViewModel
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
    )
}
