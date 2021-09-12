package io.github.bradpatras.life.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import io.github.bradpatras.life.R
import io.github.bradpatras.life.databinding.MainFragmentBinding
import io.github.bradpatras.life.ui.main.models.Dot
import io.github.bradpatras.life.ui.main.models.GameState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topBar.apply {
            inflateMenu(R.menu.main_menu)
            setOnMenuItemClickListener(this@MainFragment::menuItemClicked)
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.gameState.collect { gameState ->
                    withContext(Dispatchers.Main) {
                        when (gameState) {
                            GameState.PLAYING -> {
                                binding.topBar.menu.findItem(R.id.stop)?.isVisible = true
                                binding.topBar.menu.findItem(R.id.start)?.isVisible = false
                                binding.topBar.menu.findItem(R.id.clear)?.isVisible = false
                                binding.topBar.menu.findItem(R.id.revert)?.isVisible = false
                            }
                            GameState.EDITING -> {
                                binding.topBar.menu.findItem(R.id.stop)?.isVisible = false
                                binding.topBar.menu.findItem(R.id.start)?.isVisible = true
                                binding.topBar.menu.findItem(R.id.clear)?.isVisible = true
                                binding.topBar.menu.findItem(R.id.revert)?.isVisible = true
                            }
                        }
                    }
                }
            }

            launch {
                viewModel.gameDots.collect { dots ->
                    withContext(Dispatchers.Main) {
                        updateBoard(dots)
                    }
                }
            }
        }

        binding.boardView.tappedDotLiveData.observe(this.viewLifecycleOwner, viewModel::dotTapped)
    }

    private fun menuItemClicked(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.stop -> {
                viewModel.stopTapped()
                true
            }
            R.id.start -> {
                viewModel.startTapped()
                true
            }
            R.id.clear -> {
                viewModel.clearTapped()
                true
            }
            R.id.revert -> {
                viewModel.revertTapped()
                true
            }
            else -> false
        }
    }

    private fun updateBoard(dots: List<Dot>) {
        binding.boardView.dots = dots
        binding.boardView.invalidate()
    }

//
//    private fun startGame(): Job {
//
//        // seed alive cells
////        boardController.updateCell(5, 5, Cell.ALIVE)
////        boardController.updateCell(6, 6, Cell.ALIVE)
////        boardController.updateCell(5, 7, Cell.ALIVE)
////        boardController.updateCell(4, 7, Cell.ALIVE)
////        boardController.updateCell(6, 7, Cell.ALIVE)
//
////        boardController.updateCell(5, 5, Cell.ALIVE)
////        boardController.updateCell(5, 6, Cell.ALIVE)
////        boardController.updateCell(6, 6, Cell.ALIVE)
////        boardController.updateCell(6, 5, Cell.ALIVE)
//
//        return MainScope()
//    }
}