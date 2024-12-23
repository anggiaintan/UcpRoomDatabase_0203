package com.example.ucp2pam.ui.viewmodeldosen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2pam.DosenApp
import com.example.ucp2pam.ui.viewmodelmk.DetailMkViewModel
import com.example.ucp2pam.ui.viewmodelmk.HomeMkViewModel
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel
import com.example.ucp2pam.ui.viewmodelmk.UpdateMkViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                DosenApp().containerApp.repositoryDosen
            )
        }
        initializer {
            HomeDosenViewModel(
                DosenApp().containerApp.repositoryDosen
            )
        }
        initializer {
            MataKuliahViewModel(
                DosenApp().containerApp.repositoryMataKuliah,
                DosenApp().containerApp.repositoryDosen
            )
        }
        initializer {
            HomeMkViewModel(
                DosenApp().containerApp.repositoryMataKuliah
            )
        }
        initializer {
            DetailMkViewModel(
                createSavedStateHandle(),
                DosenApp().containerApp.repositoryMataKuliah
            )
        }
        initializer {
            UpdateMkViewModel(
                createSavedStateHandle(),
                DosenApp().containerApp.repositoryMataKuliah,
                DosenApp().containerApp.repositoryDosen
            )
        }
    }
}

fun CreationExtras.DosenApp(): DosenApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DosenApp)