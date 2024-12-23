package com.example.ucp2pam.ui.viewmodeldosen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2pam.DosenApp
import com.example.ucp2pam.ui.viewmodelmk.DetailMataKuliahViewModel
import com.example.ucp2pam.ui.viewmodelmk.HomeMatkulViewModel
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel
import com.example.ucp2pam.ui.viewmodelmk.UpdateMatkulViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                sisforApp().containerApp.repositoryDosen
            )
        }
        initializer {
            HomeDosenViewModel(
                sisforApp().containerApp.repositoryDosen
            )
        }
        initializer {
            MataKuliahViewModel(
                sisforApp().containerApp.repositoryMataKuliah,
                sisforApp().containerApp.repositoryDosen
            )
        }
        initializer {
            HomeMatkulViewModel(
                sisforApp().containerApp.repositoryMataKuliah
            )
        }
        initializer {
            DetailMataKuliahViewModel(
                createSavedStateHandle(),
                sisforApp().containerApp.repositoryMataKuliah
            )
        }
        initializer {
            UpdateMatkulViewModel(
                createSavedStateHandle(),
                sisforApp().containerApp.repositoryMataKuliah,
                sisforApp().containerApp.repositoryDosen
            )
        }
    }
}

fun CreationExtras.sisforApp(): DosenApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DosenApp)
