package com.example.giangnguyen.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.giangnguyen.presentation.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NavViewModel @Inject constructor(
    val navigationManager: NavigationManager,
) : ViewModel() {

}