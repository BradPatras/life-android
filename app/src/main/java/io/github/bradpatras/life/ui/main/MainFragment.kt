package io.github.bradpatras.life.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import io.github.bradpatras.life.R
import io.github.bradpatras.life.databinding.MainFragmentBinding
import io.github.bradpatras.life.ui.main.controllers.BoardController
import io.github.bradpatras.life.ui.main.controllers.LifeController
import io.github.bradpatras.life.ui.main.models.Cell
import io.github.bradpatras.life.ui.main.views.DotBoardView
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    private val boardController: BoardController = BoardController(Size(500, 500))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        
        lifecycleScope.launchWhenCreated {
            viewModel.isEditing.collect {
                withContext(Dispatchers.Main) {
                    setIsEditing(it)
                }
            }
        }

        startGame()
    }

    private fun setIsEditing(isEditing: Boolean) {

    }

    private fun runGameCycle() {
        boardController.updateCells { cell, neighbors ->
            LifeController.applyRules(cell, neighbors)
        }
    }

    private fun updateBoard() {
        binding.boardView.dots = boardController.getAliveCellDots().toTypedArray()
        binding.boardView.invalidate()
    }

    private fun startGame(): Job {

        // seed alive cells
//        boardController.updateCell(5, 5, Cell.ALIVE)
//        boardController.updateCell(6, 6, Cell.ALIVE)
//        boardController.updateCell(5, 7, Cell.ALIVE)
//        boardController.updateCell(4, 7, Cell.ALIVE)
//        boardController.updateCell(6, 7, Cell.ALIVE)

        boardController.updateCell(5, 5, Cell.ALIVE)
        boardController.updateCell(5, 6, Cell.ALIVE)
        boardController.updateCell(5, 7, Cell.ALIVE)

        return MainScope().launch {
            withContext(Dispatchers.IO) {
                while(isActive) {
                    runGameCycle()
                    withContext(Dispatchers.Main) {
                        updateBoard()
                    }
                    delay(100)
                }
            }
        }
    }
}