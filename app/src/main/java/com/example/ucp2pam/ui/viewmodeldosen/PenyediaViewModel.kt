package com.example.ucp2pam.ui.viewmodeldosen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2pam.DosenApp
import com.example.ucp2pam.ui.viewmodelmk.MataKuliahViewModel

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
            DetailDosenViewModel(
                createSavedStateHandle(),
                DosenApp().containerApp.repositoryDosen
            )
        }
        initializer {
            MataKuliahViewModel(
                DosenApp().containerApp.RepositoryMataKuliah,
                DosenApp().containerApp.RepositoryDosen
            )
        }
    }
}

fun CreationExtras.sisforApp(): SisforApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SisforApp)