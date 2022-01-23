package io.github.bradpatras.life.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.stopButton.setOnClickListener { viewModel.stopTapped() }
        binding.startButton.setOnClickListener { viewModel.startTapped() }
        binding.clearButton.setOnClickListener { viewModel.clearTapped() }
        binding.revertButton.setOnClickListener { viewModel.revertTapped() }
        binding.stepButton.setOnClickListener { viewModel.stepTapped() }

        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.gameState.collect { gameState ->
                    withContext(Dispatchers.Main) {
                        when (gameState) {
                            GameState.PLAYING -> {
                                binding.stopButton.visibility = View.VISIBLE
                                binding.startButton.visibility = View.GONE
                                binding.clearButton.visibility = View.GONE
                                binding.revertButton.visibility = View.GONE
                                binding.stepButton.visibility = View.GONE
                            }
                            GameState.EDITING -> {
                                binding.stopButton.visibility = View.GONE
                                binding.startButton.visibility = View.VISIBLE
                                binding.clearButton.visibility = View.VISIBLE
                                binding.revertButton.visibility = View.VISIBLE
                                binding.stepButton.visibility = View.VISIBLE
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

        ArrayAdapter<String>(
            this.requireContext(),
            R.layout.spinner_item,
            viewModel.rulesetTitles
        ).also {
            binding.rulesSpinner.adapter = it
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.rulesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.rulesetSelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.rulesetSelected(null)
            }
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