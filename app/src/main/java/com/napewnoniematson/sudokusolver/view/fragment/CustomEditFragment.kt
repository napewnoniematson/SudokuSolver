package com.napewnoniematson.sudokusolver.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.napewnoniematson.sudokusolver.R
import com.napewnoniematson.sudokusolver.view.widget.SudokuBoardView
import com.napewnoniematson.sudokusolver.viewmodel.CustomSudokuViewModel
import kotlinx.android.synthetic.main.fragment_custom_edit.*

class CustomEditFragment : Fragment(), SudokuBoardView.OnTouchListener {

    private lateinit var customViewModel: CustomSudokuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_custom_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customViewModel = CustomSudokuViewModel()
        sudokuBoardView.setOnTouchListener(this)
        //TODO CustomSudokuViewModel -> ViewModelProvider
        customViewModel.backend.selectedCellLiveData.observe(viewLifecycleOwner,
            Observer { updateSelectedCellUI(it) }
        )
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        sudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
    }

    override fun onCellTouched(row: Int, col: Int) {
        customViewModel.backend.updateSelectedCell(row, col)
    }
}