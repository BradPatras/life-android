package io.github.bradpatras.life.ui.main

import android.util.Size
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.bradpatras.life.ui.main.cache.BoardCache
import io.github.bradpatras.life.ui.main.controllers.BoardController
import io.github.bradpatras.life.ui.main.controllers.LifeController
import io.github.bradpatras.life.ui.main.models.Dot
import io.github.bradpatras.life.ui.main.models.GameState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainViewModel : ViewModel() {

    private var _gameState = MutableStateFlow(GameState.EDITING)
    private var _gameDots: MutableStateFlow<List<Dot>> = MutableStateFlow(emptyList())
    private var gameJob: Job? = null
    private val boardController: BoardController = BoardController(Size(500, 1000))
    private val boardCache = BoardCache()

    var gameState: StateFlow<GameState> = _gameState.asStateFlow()
    var gameDots: StateFlow<List<Dot>> = _gameDots.asStateFlow()

    fun dotTapped(dot: Dot) {
        if (gameState.value != GameState.EDITING) return

        boardController.toggleCell(dot.x, dot.y)
        _gameDots.value = boardController.getAliveCellDots()
    }

    fun stopTapped() {
        _gameState.value = GameState.EDITING
        gameJob?.cancel()
        gameJob = null
    }

    fun startTapped() {
        boardCache.put(boardController.getBoard())
        _gameState.value = GameState.PLAYING
        gameJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                while(isActive) {
                    runGameCycle()
                    _gameDots.value = boardController.getAliveCellDots()
                    delay(100)
                }
            }
        }
    }

    fun revertTapped() {
        boardCache.removeLatest()?.let {
            boardController.setBoard(it)
            _gameDots.value = boardController.getAliveCellDots()
        }
    }

    fun clearTapped() {
        boardController.clear()
        _gameDots.value = emptyList()
    }

    private fun runGameCycle() {
        boardController.updateCells { isAlive, neighbors ->
            LifeController.computeLivingState(isAlive, neighbors)
        }
    }
}