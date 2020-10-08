package com.napewnoniematson.sudokusolver.viewmodel

import androidx.lifecycle.ViewModel
import com.napewnoniematson.sudokusolver.logic.CustomSudokuBackend

class CustomSudokuViewModel : ViewModel() {
    val backend = CustomSudokuBackend()
}