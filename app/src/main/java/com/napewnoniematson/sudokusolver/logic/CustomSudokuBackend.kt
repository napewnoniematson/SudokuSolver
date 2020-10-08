package com.napewnoniematson.sudokusolver.logic

import androidx.lifecycle.MutableLiveData

class CustomSudokuBackend {

    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()

//    private var selectedRow = -1
    var selectedRow = -1
    private var selectedCol = -1

    init {
        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
    }

    fun updateSelectedCell(row: Int, col: Int) {
        selectedRow = row
        selectedCol = col
        selectedCellLiveData.postValue(Pair(row, col))
    }

}