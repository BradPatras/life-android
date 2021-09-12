package io.github.bradpatras.life.ui.main.controllers

import android.util.Log
import android.util.Size
import io.github.bradpatras.life.ui.main.models.Dot
import kotlin.system.measureTimeMillis

typealias IsAlive = Boolean
typealias IsAliveArray = BooleanArray
typealias Board = Array<IsAliveArray>

class BoardController(private val size: Size) {
    private var board: Board = getCleanBoard()

    fun updateCells(action: (IsAlive, IsAliveArray) -> IsAlive) {
        val newBoard = getCleanBoard()
        for (x in board.indices) {
            val boardColumn = board[x]
            for (y in boardColumn.indices) {
                newBoard[x][y] = action(board[x][y], getCellNeighbors(x, y))
            }
        }

        board = newBoard
    }

    fun clear() {
        board = getCleanBoard()
    }

    fun setBoard(board: Board) {
        this.board = board
    }

    fun getBoard(): Board {
        return board
    }

    fun getAliveCellDots(): List<Dot> {
        val aliveDots = mutableListOf<Dot>()
        for (x in board.indices) {
            val column = board[x]
            for (y in column.indices) {
                if (getCellIsAlive(x, y) == true) {
                    aliveDots.add(Dot(x, y))
                }
            }
        }

        return aliveDots
    }

    fun toggleCell(x: Int, y: Int) {
        getCellIsAlive(x, y)?.let { current ->
            updateCell(x, y, !current)
        }
    }

    private fun getCleanBoard(): Board =
            Array(size.height) { IsAliveArray(size.width) { false } }

    private fun updateCell(x: Int, y: Int, isAlive: IsAlive) {
        board.getOrNull(x)?.set(y, isAlive)
    }

    private fun getCellIsAlive(x: Int, y: Int): IsAlive? {
        return board.getOrNull(x)?.getOrNull(y)
    }

    private fun getCellNeighbors(x: Int, y: Int): IsAliveArray {
        return booleanArrayOf(
                getCellIsAlive(x - 1, y - 1) ?: false,
                getCellIsAlive(x, y - 1) ?: false,
                getCellIsAlive(x - 1, y) ?: false,
                getCellIsAlive(x + 1, y + 1) ?: false,
                getCellIsAlive(x, y + 1) ?: false,
                getCellIsAlive(x + 1, y) ?: false,
                getCellIsAlive(x - 1, y + 1) ?: false,
                getCellIsAlive(x + 1, y - 1) ?: false
        )
    }
}