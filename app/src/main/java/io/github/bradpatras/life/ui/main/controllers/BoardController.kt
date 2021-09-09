package io.github.bradpatras.life.ui.main.controllers

import android.util.Size
import io.github.bradpatras.life.ui.main.models.Cell
import io.github.bradpatras.life.ui.main.models.Dot
import io.github.bradpatras.life.ui.main.views.DotBoardView

class BoardController(private val size: Size) {
    private var board: Array<Array<Cell>> = Array(size.height) { Array(size.width) { Cell.DEAD } }

    fun updateCells(action: (Cell, Array<Cell>) -> Cell) {
        board = board.mapIndexed { x, columns ->
            columns.mapIndexed { y, cell ->
                action(cell, getCellNeighbors(x, y))
            }.toTypedArray()
        }.toTypedArray()
    }

    fun updateCell(x: Int, y: Int, cell: Cell) {
        board.getOrNull(x)?.set(y, cell)
    }

    fun getAliveCellDots(): List<Dot> {
        var aliveDots = mutableListOf<Dot>()
        board.forEachIndexed { x, columns ->
            columns.forEachIndexed { y, cell ->
                if (cell == Cell.ALIVE) {
                    aliveDots.add(Dot(x, y))
                }
            }
        }

        return aliveDots
    }

    private fun getCell(x: Int, y: Int): Cell? {
        return board.getOrNull(x)?.getOrNull(y)
    }

    private fun getCellNeighbors(x: Int, y: Int): Array<Cell> {
        return arrayOf(
                getCell(x - 1, y - 1) ?: Cell.DEAD,
                getCell(x, y - 1) ?: Cell.DEAD,
                getCell(x - 1, y) ?: Cell.DEAD,
                getCell(x + 1, y + 1) ?: Cell.DEAD,
                getCell(x, y + 1) ?: Cell.DEAD,
                getCell(x + 1, y) ?: Cell.DEAD,
                getCell(x - 1, y + 1) ?: Cell.DEAD,
                getCell(x + 1, y - 1) ?: Cell.DEAD
        )
    }
}