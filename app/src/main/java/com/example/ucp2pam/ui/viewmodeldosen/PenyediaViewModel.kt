package com.example.ucp2pam.ui.viewmodeldosen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

fun CreationExtras.sisforApp(): SisforApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SisforApp)