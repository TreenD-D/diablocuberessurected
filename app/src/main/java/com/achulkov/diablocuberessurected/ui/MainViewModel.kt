package com.achulkov.diablocuberessurected.ui

import androidx.lifecycle.ViewModel
import com.achulkov.diablocuberessurected.data.DCubeDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepo : DCubeDataRepo
) : ViewModel() {
}